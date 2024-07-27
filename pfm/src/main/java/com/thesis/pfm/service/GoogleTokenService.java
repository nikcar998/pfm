package com.thesis.pfm.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Service
public class GoogleTokenService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String verifyAndGenerateJwt(String idTokenString) throws Exception {
        // Use recommended GsonFactory for JSON parsing
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        NetHttpTransport httpTransport = new NetHttpTransport.Builder().build();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            return generateJwtToken(email);
        } else {
            throw new Exception("Invalid ID token.");
        }
    }

    private String generateJwtToken(String email) {
        // Consider using a stronger algorithm like HS512 or RS256 if security is critical
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SignatureAlgorithm.HS256, jwtSecret) // Consider a stronger algorithm
                .compact();
    }
}
