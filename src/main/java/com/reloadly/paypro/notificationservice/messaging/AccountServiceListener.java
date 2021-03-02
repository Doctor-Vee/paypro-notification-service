package com.reloadly.paypro.notificationservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reloadly.paypro.notificationservice.constant.EventTopicConstant;
import com.reloadly.paypro.notificationservice.payload.event.TransactionEvent;
import com.reloadly.paypro.notificationservice.payload.event.UserCreationEvent;
import com.reloadly.paypro.notificationservice.payload.event.UserUpdateEvent;
import com.reloadly.paypro.notificationservice.persistence.model.EventRecord;
import com.reloadly.paypro.notificationservice.persistence.repository.EventRecordRepository;
import com.reloadly.paypro.notificationservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
public class AccountServiceListener {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EventRecordRepository eventRecordRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = EventTopicConstant.USER_CREATION)
    public void handleUserCreationEvent(String payload) {
        log.info("User Creation Event: " + payload);
        EventRecord record = new EventRecord();
        record.setTopic(EventTopicConstant.USER_CREATION);
        record.setPayload(payload);
        eventRecordRepository.save(record);
        UserCreationEvent event = new UserCreationEvent();
        try {
            event = objectMapper.readValue(payload, UserCreationEvent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String subject = "PayPro account created successfully";
        String message = "Congratulations, you have successfully created an account with PayPro. Your account number is " + event.getAccountNumber() + ". <br> " +
                "As a gesture of goodwill from us, your account has been credited with <b>$10,000.00.</b> Thanks for joining us.";
        try {
            String response = notificationService.sendMessageToGivenEmail(subject, message, event.getEmail());
            log.info("Mailgun: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = EventTopicConstant.TRANSACTION_COMPLETED)
    public void handleCompletedTransactionEvent(String payload) {
        log.info("Transaction Completed Event: " + payload);
        EventRecord record = new EventRecord();
        record.setTopic(EventTopicConstant.TRANSACTION_COMPLETED);
        record.setPayload(payload);
        eventRecordRepository.save(record);
        TransactionEvent event = new TransactionEvent();
        try {
            event = objectMapper.readValue(payload, TransactionEvent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String senderSubject = "Debit Alert!";
        String senderMessage = "You just transferred <b>$" + event.getAmount() + "</b> to " + event.getReceiverName() + ". <br> <b>Narration:</b> " + event.getNarration();
        try {
            String response = notificationService.sendMessageToGivenEmail(senderSubject, senderMessage, event.getSenderEmail());
            log.info("Mailgun: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String receiverSubject = "Credit Alert!";
        String receiverMessage = "You just received <b>$" + event.getAmount() + "</b> from " + event.getSenderName() + ". <br> <b>Narration:</b> " + event.getNarration();
        try {
            String response = notificationService.sendMessageToGivenEmail(receiverSubject, receiverMessage, event.getReceiverEmail());
            log.info("Mailgun: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = EventTopicConstant.USER_UPDATE)
    public void handleUserUpdateEvent(String payload) {
        log.info("User Update Event: " + payload);
        EventRecord record = new EventRecord();
        record.setTopic(EventTopicConstant.USER_UPDATE);
        record.setPayload(payload);
        eventRecordRepository.save(record);
        UserUpdateEvent event = new UserUpdateEvent();
        try {
            event = objectMapper.readValue(payload, UserUpdateEvent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String subject = "Details updated successfully";
        String message = "You have successfully updated your details. ";
        if(!ObjectUtils.isEmpty(event.getUsername())) message += " <br> <b>Username:</b> " + event.getUsername();
        if(!ObjectUtils.isEmpty(event.getPhoneNumber())) message += " <br> <b>Phone Number:</b> " + event.getPhoneNumber();
        try {
            String response = notificationService.sendMessageToGivenEmail(subject, message, event.getEmail());
            log.info("Mailgun: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
