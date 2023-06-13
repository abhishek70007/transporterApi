package com.springboot.FirebaseUserApi;
import java.util.*;
import com.google.api.services.storage.Storage.BucketAccessControls.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.extern.slf4j.Slf4j;


@org.springframework.stereotype.Service
@Slf4j
public class Service {

    //getting uid by email
    public Entity getByEmail(String email) throws  FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
        if(userRecord==null){
            EntityNotFoundException ex = new EntityNotFoundException(Entity.class, "email", email);
            log.error(String.valueOf(ex));
            throw ex;
        }
        Entity response=new Entity();
        response.setName(userRecord.getDisplayName());
        response.setEmail((userRecord.getEmail()));
        response.setUid(userRecord.getUid());
        response.setPhotoURL(userRecord.getPhotoUrl());
        response.setPhoneNo(userRecord.getPhoneNumber());
        response.setProviderId(userRecord.getProviderId());
        response.setTenantId(userRecord.getTenantId());
        response.setEmailVerified(userRecord.isEmailVerified());
        return response;
    }
    
    //getting uid by phone number
    public Entity getByPhoneno(String phoneNo) throws  FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByPhoneNumber(phoneNo);
        if(userRecord==null){
            EntityNotFoundException ex = new EntityNotFoundException(Entity.class, "phoneNo", phoneNo);
            log.error(String.valueOf(ex));
            throw ex;
        }
        Entity response=new Entity();
        response.setName(userRecord.getDisplayName());
        response.setEmail((userRecord.getEmail()));
        response.setUid(userRecord.getUid());
        response.setPhotoURL(userRecord.getPhotoUrl());
        response.setPhoneNo(userRecord.getPhoneNumber());
        response.setProviderId(userRecord.getProviderId());
        response.setTenantId(userRecord.getTenantId());
        response.setEmailVerified(userRecord.isEmailVerified());
        response.setEmailVerified(userRecord.isEmailVerified());
        return response;
    }

//    Getting User by uid
    public Entity getByUid(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        if(userRecord==null){
            EntityNotFoundException ex = new EntityNotFoundException(Entity.class, "uid", uid);
            log.error(String.valueOf(ex));
            throw ex;
        }
        Entity response=new Entity();
        response.setName(userRecord.getDisplayName());
        response.setEmail((userRecord.getEmail()));
        response.setUid(userRecord.getUid());
        response.setPhotoURL(userRecord.getPhotoUrl());
        response.setPhoneNo(userRecord.getPhoneNumber());
        response.setProviderId(userRecord.getProviderId());
        response.setTenantId(userRecord.getTenantId());
        response.setEmailVerified(userRecord.isEmailVerified());
        response.setEmailVerified(userRecord.isEmailVerified());
        return response;
    }
}
