package com.ttn.bootcamp.service;

public interface EmailService {
    void sendEmail(String sendTo, String subject, String body);
}
