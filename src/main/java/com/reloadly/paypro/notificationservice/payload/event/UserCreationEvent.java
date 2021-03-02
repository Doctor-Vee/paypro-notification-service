package com.reloadly.paypro.notificationservice.payload.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreationEvent {

    private String email;

    private String username;

    private String phoneNumber;

    private String accountNumber;

}
