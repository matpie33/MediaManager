package org.travelling.ticketer.utility;

import io.nayuki.qrcodegen.QrCode;
import org.springframework.stereotype.Component;
import org.travelling.ticketer.constants.Filenames;
import org.travelling.ticketer.dao.TicketDao;
import org.travelling.ticketer.dto.QrCodeContentDTO;
import org.travelling.ticketer.entity.Ticket;
import org.travelling.ticketer.security.SecurityManager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Component
public class QrCodeGenerator {

    private final SecurityManager securityManager;

    private final TicketDao ticketDao;

    public QrCodeGenerator(SecurityManager securityManager, TicketDao ticketDao) {
        this.securityManager = securityManager;
        this.ticketDao = ticketDao;
    }

    public void getQrCode(Long ticketId, Long userId, Long connectionId, String iv) throws IOException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, InvalidKeyException {
        IvParameterSpec ivspec = securityManager.convertIvStringToObject(iv);
        String content = userId+" "+connectionId;
        String encryptedContent = securityManager.encrypt(content, ivspec);
        String qrCodeContent = ticketId + ":" + encryptedContent;
        QrCode qrCode = QrCode.encodeText(qrCodeContent, QrCode.Ecc.HIGH);
        BufferedImage img = toImage(qrCode, 10, 4);
        ImageIO.write(img, "png", new File(Filenames.QR_CODE));
    }

    public QrCodeContentDTO decodeInformation (String qrCodeData) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, KeyStoreException, IOException, InvalidKeyException {
        if (!qrCodeData.contains(":")){
            QrCodeContentDTO qrCodeContentDTO = new QrCodeContentDTO();
            qrCodeContentDTO.setValidContent(false);
            return qrCodeContentDTO;
        }
        int indexOfColon = qrCodeData.indexOf(":");
        if (indexOfColon == qrCodeData.length()-1){
            QrCodeContentDTO qrCodeContentDTO = new QrCodeContentDTO();
            qrCodeContentDTO.setValidContent(false);
            return qrCodeContentDTO;
        }
        String ticketId = qrCodeData.substring(0, indexOfColon);
        String decryptedData= qrCodeData.substring(indexOfColon+1);
        long ticketIdL = Long.parseLong(ticketId);
        Ticket ticket = ticketDao.findById(ticketIdL).orElseThrow(ExceptionBuilder.createIllegalArgumentException("Ticket not found"));
        String initializationVector = ticket.getInitializationVector();
        String data = securityManager.decrypt(decryptedData, securityManager.convertIvStringToObject(initializationVector));
        if (!data.contains(" ")){
            QrCodeContentDTO qrCodeContentDTO = new QrCodeContentDTO();
            qrCodeContentDTO.setValidContent(false);
            return qrCodeContentDTO;
        }
        String[] dataSplitByWhitespace = data.split(" ");
        if (dataSplitByWhitespace.length==1){
            QrCodeContentDTO qrCodeContentDTO = new QrCodeContentDTO();
            qrCodeContentDTO.setValidContent(false);
            return qrCodeContentDTO;
        }
        long userId = Long.parseLong(dataSplitByWhitespace[0]);
        long connectionId = Long.parseLong(dataSplitByWhitespace[1]);
        QrCodeContentDTO qrCodeContent = new QrCodeContentDTO();
        qrCodeContent.setConnectionId(connectionId);
        qrCodeContent.setUserId(userId);
        qrCodeContent.setTicketId(ticketIdL);
        qrCodeContent.setValidContent(true);
        return qrCodeContent;

    }

    private static BufferedImage toImage(QrCode qr, int scale, int border) {
        return toImage(qr, scale, border, 0xFFFFFF, 0x000000);
    }

    private static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0)
            throw new IllegalArgumentException("Value out of range");
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale)
            throw new IllegalArgumentException("Scale or border too large");

        BufferedImage result = new BufferedImage((qr.size + border * 2) * scale, (qr.size + border * 2) * scale, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return result;
    }

}
