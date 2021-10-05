package com.manager.password.service;

import com.manager.password.model.Entry;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface PasswordService {

    Entry read(String key) throws IOException;
    Boolean update(String key,String value) throws IOException;
    Boolean delete(String key) throws IOException;
}
