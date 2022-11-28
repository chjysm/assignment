package com.api.assignment.util;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Encorder {

    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder(String type) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new StandardPasswordEncoder());

        return new DelegatingPasswordEncoder(type, encoders);
    }

    public String sha256Encoding(String password) {
        String encPassword = "";
        encPassword = passwordEncoder("sha256").encode( password );
        return encPassword;
    }

    public boolean sha256Matching(String password, String encPassword) {
        return passwordEncoder("sha256").matches(password, encPassword);
    }

}
