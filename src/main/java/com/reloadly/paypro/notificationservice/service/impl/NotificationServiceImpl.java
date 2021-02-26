package com.reloadly.paypro.notificationservice.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.reloadly.paypro.notificationservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${mailgun-api-key}")
    private String apiKey;

    @Value("${mailgun-domain-name}")
    private String domainName;

    @Override
    public String sendMessageToGivenEmail(String subject, String message, String emailAddress) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + domainName + "/messages")
                .basicAuth("api", apiKey)
                .queryString("from", "Doctor Vee from PayPro <doctorvee@paypro.com>")
                .queryString("to", emailAddress)
                .queryString("subject", subject != null ? subject : "Notification from PayPro")
                .queryString("html", "<p style='font-family:arial; font-size:1.5em; color:blue;text-align:center;'>"
                        + message +
                        "</h3> <footer style='background-color:skyblue; text-align:center'>✔ PayPro</footer>")
                .asJson();
        log.info(String.valueOf(response.getBody()));
        String responseString = "";
        if (response.getBody().getObject().has("id")) {
            responseString = "Email Successfully Sent 👍";
        } else {
            responseString = "An error occurred 👎";
        }
        ;
        return responseString;
    }
}
