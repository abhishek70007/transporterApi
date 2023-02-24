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
        URL url = new URL(firebaseUrl);
        InputStream refreshToken = url.openStream();

        FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://liveasytransporterapp-default-rtdb.firebaseio.com")
                    .build();

        FirebaseApp.initializeApp(options);
    }
}

