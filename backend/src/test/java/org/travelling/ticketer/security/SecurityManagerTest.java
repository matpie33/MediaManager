package org.travelling.ticketer.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.crypto.spec.IvParameterSpec;

public class SecurityManagerTest {

    @Test
    public void shouldEncode () throws Exception{
        SecurityManager securityManager = new SecurityManager();
        IvParameterSpec iv = securityManager.generateIv();
        String toEncrypt = "ajsdfkjlaskd sjadlkfjsalkdf";
        String encrypted = securityManager.encrypt(toEncrypt, iv);
        String decrypted = securityManager.decrypt(encrypted, iv);
        Assertions.assertThat(decrypted).isEqualTo(toEncrypt);

        String decrypt = securityManager.decrypt("Fm+VKkNqwVqS7HVKo/vkmw==", new IvParameterSpec(Base64.decodeBase64("3tzQatfneLiAVuEa8nH6TA==")));
        Assertions.assertThat(decrypt).isEqualTo("1029,1");
    }

}