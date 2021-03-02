package com.reloadly.paypro.notificationservice.payload.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionEvent {

    private String senderName;

    private String senderEmail;

    private String receiverName;

    private String receiverEmail;

    private BigDecimal amount;

    private String narration;
}
