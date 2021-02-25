package com.reloadly.paypro.notificationservice.controllers;

import com.reloadly.paypro.notificationservice.payload.request.NotificationRequest;
import com.reloadly.paypro.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request){
        try{
        String response = notificationService.sendMessageToGivenEmail(request.getSubject(), request.getMessage(), request.getEmailAddress());
        return ResponseEntity.ok(response);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
