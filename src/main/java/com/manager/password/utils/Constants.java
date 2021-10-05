package com.manager.password.utils;

import org.springframework.stereotype.Component;

import javax.crypto.spec.IvParameterSpec;

@Component
public class Constants {

    public static final String DEFAULT_SALT = "123456789";
    public static final IvParameterSpec DEFAULT_IV=new IvParameterSpec(new byte[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16});
}
