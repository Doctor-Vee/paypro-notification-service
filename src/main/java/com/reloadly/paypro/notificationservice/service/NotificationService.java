package com.reloadly.paypro.notificationservice.service;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface NotificationService {

    public String sendMessageToGivenEmail(String subject, String message, String emailAddress) throws UnirestException;

}
