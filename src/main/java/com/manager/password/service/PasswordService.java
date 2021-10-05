package com.manager.password.service;

import com.manager.password.model.Entry;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface PasswordService {

    Entry read(Entry entry,Boolean isEncrypt,String pHash) throws Exception;
    Boolean update(Entry entry,Boolean isEncrypt,String pHash) throws Exception;
    Boolean delete(Entry entry,Boolean isEncrypt,String pHash) throws IOException;
    String getHash(String plainText);
}
