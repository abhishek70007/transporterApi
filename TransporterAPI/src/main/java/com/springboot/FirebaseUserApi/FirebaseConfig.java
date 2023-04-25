package com.springboot.FirebaseUserApi;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


@Configuration
public class FirebaseConfig {
    public FirebaseConfig(@Value("${FIREBASE_URL}") String firebaseUrl) throws IOException {
        
        //getting url of json file
        URL url = new URL(firebaseUrl);
        
        //getting json file as a stream 
        InputStream refreshToken = url.openStream();

        //authentication with firebase
        FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://shipperwebapp-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .build();
    
        //initializing the app
        FirebaseApp.initializeApp(options);
    }
}

