package com.springboot.FirebaseUserApi;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entity {
    private String uid;
    private String email;
    private String phoneNo;
    private String name;
    private String photoURL;
    private String providerId;
    private String tenantId;
    private boolean isEmailVerified;
}
