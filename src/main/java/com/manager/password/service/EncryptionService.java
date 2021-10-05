package com.manager.password.service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface EncryptionService<K extends Serializable,V extends Serializable> {

    V encrypt(K date,String pHash) throws Exception;
    K decrypt(V encData,String pHash) throws Exception;

}
