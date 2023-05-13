package org.travelling.ticketer.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.travelling.ticketer.constants.Filenames;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.util.Objects;

@Component
public class SecurityManager {

    private final static String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String AES = "AES";
    public static final int IV_SIZE = 16;

    public SecretKey getKey () throws KeyStoreException, IOException {
        byte[] bytes = Objects.requireNonNull(getClass().getResourceAsStream("/"+ Filenames.SECURITY_KEY)).readAllBytes();
        return new SecretKeySpec(bytes, AES);
    }


    public  IvParameterSpec generateIv() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public String convertIvToString (IvParameterSpec ivParameterSpec){
        return new String(Base64.encodeBase64(ivParameterSpec.getIV()));
    }

    public IvParameterSpec convertIvStringToObject (String iv){
        return new IvParameterSpec(Base64.decodeBase64(iv.getBytes()));
    }

    public  String encrypt( String input,
                                 IvParameterSpec iv) throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, KeyStoreException, IOException {

        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.encodeBase64String(cipherText);
    }

    public  String decrypt(String cipherText,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, KeyStoreException, IOException {

        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getKey(), iv);
        byte[] plainText = cipher.doFinal(Base64.decodeBase64(cipherText));
        return new String(plainText);
    }

}
