//package com.manager.password.service.impl;
//
//import com.manager.password.service.IEncryption;
//import javax.crypto.*;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.PBEKeySpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.IOException;
//import java.io.Serializable;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.KeySpec;
//
//public class AESEncryption implements IEncryption {
//
//    private final String salt;
//    private final IvParameterSpec ivParameterSpec;
//    private final String password;
//
//    public AESEncryption(String salt, IvParameterSpec ivParameterSpec, String password) {
//        this.salt = salt;
//        this.ivParameterSpec = ivParameterSpec;
//        this.password = password;
//    }
//
//    @Override
//    public Serializable encrypt(Serializable data) throws Exception{
//        try {
//            SecretKey secretKey = getKeyFromPassword(password,salt);
//            return encryptObject("AES",data,secretKey,ivParameterSpec);
//        } catch (Exception e){
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    @Override
//    public Serializable decrypt(Serializable data) throws Exception{
//        try {
//            SecretKey secretKey = getKeyFromPassword(password,salt);
//            return decryptObject("AES",(SealedObject) data,secretKey,ivParameterSpec);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    public SecretKey getKeyFromPassword(String password, String salt)
//            throws NoSuchAlgorithmException, InvalidKeySpecException {
//
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
//        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
//                .getEncoded(), "AES");
//        return secret;
//    }
//
//    public IvParameterSpec generateIv() {
//        byte[] iv = new byte[16];
//        new SecureRandom().nextBytes(iv);
//        return new IvParameterSpec(iv);
//    }
//
//    public SealedObject encryptObject(String algorithm, Serializable object,
//                                      SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException,
//            NoSuchAlgorithmException, InvalidAlgorithmParameterException,
//            InvalidKeyException, IOException, IllegalBlockSizeException {
//
//        Cipher cipher = Cipher.getInstance(algorithm);
//        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
//        SealedObject sealedObject = new SealedObject(object, cipher);
//        return sealedObject;
//    }
//
//    public static Serializable decryptObject(String algorithm, SealedObject sealedObject,
//                                             SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException,
//            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
//            ClassNotFoundException, BadPaddingException, IllegalBlockSizeException,
//            IOException {
//
//        Cipher cipher = Cipher.getInstance(algorithm);
//        cipher.init(Cipher.DECRYPT_MODE, key, iv);
//        Serializable unsealObject = (Serializable) sealedObject.getObject(cipher);
//        return unsealObject;
//    }
//
//}
