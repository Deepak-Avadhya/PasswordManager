package com.manager.password.service.impl;

import com.manager.password.model.Entry;
import com.manager.password.service.EncryptionService;
import com.manager.password.service.PasswordService;
import com.manager.password.storage.diskmap.DiskBackedMap;
import com.manager.password.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Map;

@Component
public class PasswordServiceImpl implements PasswordService {

    private Map<String, String> storage = new DiskBackedMap<>(Constants.DEFAULT_DATA_DIR);

    @Autowired
    private EncryptionService<String, String> encryptionService;

    private Entry read(Entry entry) throws IOException {
        String key = entry.getKey();
        if (storage.containsKey(key)) {
            entry.setValue(storage.get(key));
            return entry;
        }
        return entry;
    }

    private Boolean update(Entry entry) throws IOException {
        storage.put(entry.getKey(), entry.getValue());
        return true;
    }

    private Boolean delete(Entry entry) throws IOException {
        if (storage.containsKey(entry.getKey())) {
            storage.remove(entry.getKey());
        }
        return true;
    }

    private Entry encrypt(Entry entry, String pHash) throws Exception {
        try {
            if (entry.getKey() != null) entry.setKey(encryptionService.encrypt(entry.getKey(), pHash));
            if (entry.getValue() != null) entry.setValue(encryptionService.encrypt(entry.getValue(), pHash));
            return entry;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Entry decrypt(Entry entry, String pHash) throws Exception {
        try {
            if (entry.getKey() != null) entry.setKey(encryptionService.decrypt(entry.getKey(), pHash));
            if (entry.getValue() != null) entry.setValue(encryptionService.decrypt(entry.getValue(), pHash));
            return entry;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Entry read(Entry entry, Boolean isEncrypt, String pHash) throws Exception {
        if (!isKeyValid(entry) || !isPhashValid(pHash)) throw new InvalidKeyException("entry or phash is invalid for given dir");
        if (isEncrypt) {
            return decrypt(read(encrypt(entry, pHash)), pHash);
        } else {
            return read(entry);
        }
    }

    @Override
    public Boolean update(Entry entry, Boolean isEncrypt, String pHash) throws Exception {
        if (!isEntryValid(entry) || !isPhashValid(pHash)) throw new InvalidKeyException("entry or phash is invalid for given dir");
        updatePhase(pHash);
        if (isEncrypt) {
            return update(encrypt(entry, pHash));
        } else {
            return update(entry);
        }
    }

    @Override
    public Boolean delete(Entry entry, Boolean isEncrypt, String pHash) throws IOException {
        return null;
    }

    @Override
    public String getHash(String plainText) {
        int hash=7;
        for (int i = 0; i < plainText.length(); i++) {
            hash = hash*31 + plainText.charAt(i);
        }
        return String.valueOf(hash);
    }

    public Boolean isKeyValid(Entry entry) {
        if (entry != null && entry.getKey() != null && !Constants.PHASH_KEY.equals(entry.getKey())) return true;
        return false;
    }

    public Boolean isEntryValid(Entry entry) {
        if (isKeyValid(entry) && entry.getValue() != null) return true;
        return false;
    }

    public Boolean isPhashValid(String pHash) {
        if (StringUtils.isEmpty(pHash)) return false;
        if (storage.containsKey(Constants.PHASH_KEY) && !pHash.equals(storage.get(Constants.PHASH_KEY))) {
            return false;
        }
        return true;
    }
    public void updatePhase(String pHash){
        storage.put(Constants.PHASH_KEY, pHash);
    }
}
