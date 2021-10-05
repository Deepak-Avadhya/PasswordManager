package com.manager.password.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.manager.password.model.Entry;
import com.manager.password.service.PasswordService;
import com.manager.password.utils.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;

@Controller
@RequestMapping("/passwordManager")
public class PasswordController {

    private Logger logger	= LoggerFactory.getLogger(PasswordController.class);
    private Gson gson = new GsonBuilder().create();

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private AESUtil aesUtil;

    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
    public ResponseEntity healthCheckup() {
        logger.info("Request received in /healthcheck");
        return ResponseEntity.ok("200");
    }

    @RequestMapping(value = {"/create","/update"}, method = RequestMethod.POST)
    public ResponseEntity<String> update(HttpServletRequest httpServletRequest,
                                         @RequestParam(required =  true) Boolean isEncrypt,
                                         @RequestParam(required =  true) String key,
                                         @RequestParam(required =  true) String value
                                         ) {
        logger.info("Request received in /create /update");
        String password=httpServletRequest.getHeader("password");
        if(isEncrypt && StringUtils.isEmpty(password)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Boolean res = passwordService.update(new Entry(key,value),isEncrypt,passwordService.getHash(password));
            if(res)return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (InvalidKeyException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = {"/read"}, method = RequestMethod.POST)
    public ResponseEntity<String> applyLeave(HttpServletRequest httpServletRequest,
                                             @RequestParam(required =  true) Boolean isEncrypt,
                                             @RequestParam(required =  true) String key
                                             ) {
        logger.info("Request received in /read");
        String password=httpServletRequest.getHeader("password");
        if(isEncrypt && StringUtils.isEmpty(password)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Entry res = passwordService.read(new Entry(key,null),isEncrypt,passwordService.getHash(password));
            return new ResponseEntity<>(gson.toJson(res),HttpStatus.ACCEPTED);
        } catch (InvalidKeyException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }
}
