package com.manager.password.service.impl;

import com.manager.password.model.Entry;
import com.manager.password.service.PasswordService;
import com.manager.password.storage.diskmap.DiskBackedMap;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Component
public class PasswordServiceImpl implements PasswordService {

    private Map<String,String> storage;

    @PostConstruct
    public void init(){
        storage = new DiskBackedMap<>("DEFAULT_DATA_FILE");
    }


    @Override
    public Entry read(String key) throws IOException {
        if(StringUtils.isEmpty(key))throw  new IOException("Empty key");
        if(storage.containsKey(key)){
            return new Entry(key,storage.get(key));
        }
        throw new IOException("No such key:"+key+" found exception");
    }

    @Override
    public Boolean update(String key,String value) throws IOException {
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(value))throw  new IOException("Empty key");
        storage.put(key,value);
        return true;
    }



    @Override
    public Boolean delete(String key) throws IOException {
        if(StringUtils.isEmpty(key))throw  new IOException("Empty key");
        if(storage.containsKey(key)){
            storage.remove(key);
        }
        return true;
    }

}
