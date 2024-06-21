package org.defalt.core.util;

import org.defalt.core.context.CurrentApplicationContext;
import org.defalt.core.context.auth.UserSecurityContext;
import org.defalt.core.entity.User;
import org.defalt.core.util.exception.CipheringProcessException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public final class CipherUtils {
    private final Cipher cipher;

    public static CipherUtils getInstance() {
        return CurrentApplicationContext.getBean(CipherUtils.class);
    }

    private CipherUtils() throws Exception {
        byte[] password = "1111111111111111".getBytes();
        byte[] iv = "1111111111111111".getBytes();

        SecretKey secretKey = new SecretKeySpec(password,"AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
    }

    public String encryptAccessForMyself(String media) {
        User user = UserSecurityContext.getCurrentUser().getUser();
        return encryptAccess(user, media, user, TimeUtils.plusDays(new Date(), 1));
    }

    public String encryptAccessForMyself(String media, Date date) {
        User user = UserSecurityContext.getCurrentUser().getUser();
        return encryptAccess(user, media, user, date);
    }

    public String encryptAccess(User owner, String media, User user, Date expiration) {
        if (media == null)
            return null;

        try {
            return encryptAccess("%s;%s;%s;%d".formatted(owner.getUsername(), media, user.getUsername(), expiration.getTime()));
        } catch (CipheringProcessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encryptAccess(String accessToken) throws CipheringProcessException {
        try {
            return Base64.getEncoder().encodeToString(cipher.doFinal(accessToken.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new CipheringProcessException();
        }
    }
}
