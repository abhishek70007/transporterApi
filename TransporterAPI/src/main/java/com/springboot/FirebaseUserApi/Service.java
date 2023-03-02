package com.springboot.FirebaseUserApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;



@org.springframework.stereotype.Service
public class Service {

    public String getByPhoneno(String phoneNumber) throws  FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByPhoneNumber(phoneNumber);
           return userRecord.getUid();
        
        
    }
}
