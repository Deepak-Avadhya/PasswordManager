package com.manager.password.utils;

import org.springframework.stereotype.Component;

import javax.crypto.spec.IvParameterSpec;

@Component
public class Constants {

    public static final String DEFAULT_SALT = "123456789";
    public static final IvParameterSpec DEFAULT_IV=new IvParameterSpec(new byte[]{5, -111, 102, 40, 79, 15, -128, 38, -80, 54, 28, -107, -69, 50, 67, 46});
    public static final String PHASH_KEY="PHASH_KEY";
    public static final String DEFAULT_DATA_DIR="DEFAULT_DATA_DIR";
}
