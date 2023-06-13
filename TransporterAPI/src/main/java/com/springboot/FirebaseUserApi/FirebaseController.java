package com.springboot.FirebaseUserApi;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.events.EndElement;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
public class FirebaseController {

    @Autowired
    public Service service;
    
    //get uid from email
    @GetMapping("/email/{email}")
    public ResponseEntity<Entity> getUserByEmail(@PathVariable String email) throws InterruptedException, ExecutionException, FirebaseAuthException {
       //returning uid as a response       
       return new ResponseEntity<>(service.getByEmail(email), HttpStatus.OK);
    }
    
    //get uid from phoneno
    @GetMapping("/phoneNo/{phoneNo}")
    public ResponseEntity<Entity> getUserByPhoneNo(@PathVariable String phoneNo) throws InterruptedException, ExecutionException, FirebaseAuthException {
       //returning uid as a response       
       return new ResponseEntity<>(service.getByPhoneno(phoneNo),HttpStatus.OK);
    }

    @GetMapping("/uid/{uid}")
    public ResponseEntity<Entity> getUserbyUid(@PathVariable String uid) throws FirebaseAuthException {
        return new ResponseEntity<>(service.getByUid(uid), HttpStatus.OK);
    }
}