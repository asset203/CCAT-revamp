package com.asset.ccat.springcloudconfigserver.utils;

import com.asset.ccat.springcloudconfigserver.database.dao.AdmSystemPropertiesDao;
import com.asset.ccat.springcloudconfigserver.loggers.CCATLogger;
import com.asset.ccat.springcloudconfigserver.models.SystemConfigurationModel;
import com.asset.ccat.springcloudconfigserver.models.requests.UpdateConfigurationsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class PasswordEncryptorUtil {
    @Autowired
    private AdmSystemPropertiesDao admSystemPropertiesDao;
    public List<SystemConfigurationModel> passwordEncryptor(UpdateConfigurationsRequest request) throws Exception {
        List<SystemConfigurationModel> configList = request.getConfigurations();
        String plainPassword = "";
        String cipherPassword = "";
        final String secretKey = "secrete";
        List<SystemConfigurationModel> resultConfigs = new ArrayList<>();
        for(SystemConfigurationModel model : configList){
            if(model.getValueType() == 4 || model.getValueType().equals("4")){
                if(Objects.nonNull(model.getValue())) {
                    plainPassword = model.getValue();
                    cipherPassword = encrypt(plainPassword,secretKey);
                    model.setValue(cipherPassword);
                }else {
                    HashMap<String, SystemConfigurationModel> currentConfigs = admSystemPropertiesDao.retrieveSystemPropertiesPasswords(request.getProfile(), request.getLabel());
                    String value = currentConfigs.get(model.getKey()).getValue();
                    model.setValue(value);
                }
            }

            resultConfigs.add(model);
        }
        return resultConfigs;
    }

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    public void prepareSecreteKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String strToEncrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

}
