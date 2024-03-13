package com.example.cookscorner.services;

public interface EmailService {
    public void sendVerificationEmail(String to, String subject, String text);

    public String buildEmail(String name, String link, String type);
}
