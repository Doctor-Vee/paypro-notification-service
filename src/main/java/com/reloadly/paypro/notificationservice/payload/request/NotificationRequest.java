package com.reloadly.paypro.notificationservice.payload.request;

import lombok.Data;

@Data
public class NotificationRequest {

    private String emailAddress;
    private String subject;
    private String message;

}
