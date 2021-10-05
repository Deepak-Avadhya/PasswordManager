package com.manager.password.service.impl;

import com.manager.password.service.EncryptionService;
import com.manager.password.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
public class EncryptionServiceImpl implements EncryptionService<String,String> {

    @Autowired
    private AESUtil aesUtil;

    @Override
    public String encrypt(String date, String pHash) throws Exception {
        try {
            return aesUtil.encrypt(date,pHash);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Override
    public String decrypt(String encData, String pHash) throws Exception{
        try {
            return aesUtil.decrypt(encData,pHash);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
