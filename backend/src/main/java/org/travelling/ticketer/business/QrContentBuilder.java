package org.travelling.ticketer.business;

import org.springframework.stereotype.Component;
import org.travelling.ticketer.dto.QrCodeContentDTO;
import org.travelling.ticketer.security.SecurityManager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class QrContentBuilder {

    public static final char ENCRYPTED_CONTENT_SEPARATOR = ' ';
    public static final char PLAINTEXT_SEPARATOR = ':';
    private final SecurityManager securityManager;
    private final TicketsService ticketsService;

    public QrContentBuilder(SecurityManager securityManager, TicketsService ticketsService) {
        this.securityManager = securityManager;
        this.ticketsService = ticketsService;
    }


    public String build(QrCodeContentDTO qrCodeContentDTO, String iv) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, IOException, InvalidKeyException {
        IvParameterSpec ivspec = securityManager.convertIvStringToObject(iv);
        String contentToEncrypt = "" + qrCodeContentDTO.getUserId() + ENCRYPTED_CONTENT_SEPARATOR + qrCodeContentDTO.getConnectionId();
        String encryptedContent = securityManager.encrypt(contentToEncrypt, ivspec);
        String qrCodeContent = "" +qrCodeContentDTO.getTicketId() + PLAINTEXT_SEPARATOR + encryptedContent;
        return qrCodeContent;
    }

    public QrCodeContentDTO parseContent (String qrCodeData ) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, KeyStoreException, IOException, InvalidKeyException {
        Collection<String> mainContent = decode(qrCodeData, PLAINTEXT_SEPARATOR);
        if (mainContent.isEmpty()){
            return createEmptyQrContent();
        }

        Iterator<String> iterator = mainContent.iterator();
        long ticketId = Long.parseLong(iterator.next());
        String encryptedData = iterator.next();
        String decryptedData = decryptData(encryptedData, ticketId);

        Collection<String> decryptedValues = decode(decryptedData, ENCRYPTED_CONTENT_SEPARATOR);
        if (decryptedValues.isEmpty()){
            return createEmptyQrContent();
        }
        Iterator<String> decryptedValuesIterator = decryptedValues.iterator();
        long userId = Long.parseLong(decryptedValuesIterator.next());
        long connectionId = Long.parseLong(decryptedValuesIterator.next());

        return createQrContentDTO(ticketId, userId, connectionId);

    }

    private QrCodeContentDTO createEmptyQrContent() {
        QrCodeContentDTO qrCodeContentDTO = new QrCodeContentDTO();
        qrCodeContentDTO.setValidContent(false);
        return qrCodeContentDTO;
    }

    private QrCodeContentDTO createQrContentDTO(long ticketId, long userId, long connectionId) {
        QrCodeContentDTO qrCodeContent = new QrCodeContentDTO();
        qrCodeContent.setConnectionId(connectionId);
        qrCodeContent.setUserId(userId);
        qrCodeContent.setTicketId(ticketId);
        qrCodeContent.setValidContent(true);
        return qrCodeContent;
    }

    private String decryptData(String encryptedData, long ticketId) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, KeyStoreException, IOException {
        String initializationVector = ticketsService.getIv(ticketId);
        return securityManager.decrypt(encryptedData, securityManager.convertIvStringToObject(initializationVector));
    }

    private Collection<String> decode (String text, char separator){
        int indexOfSeparator = text.indexOf(separator);
        if (!text.contains("" + separator) || indexOfSeparator == text.length() - 1){
            return Collections.emptyList();
        }
        String[] separatedText = text.split("" + separator);
        return Arrays.stream(separatedText).collect(Collectors.toCollection(LinkedHashSet::new));

    }

}
