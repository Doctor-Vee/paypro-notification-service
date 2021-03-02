package com.reloadly.paypro.notificationservice.payload.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateEvent {

    private String email;

    private String username;

    private String phoneNumber;

}
