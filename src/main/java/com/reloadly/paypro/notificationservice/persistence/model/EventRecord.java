package com.reloadly.paypro.notificationservice.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "event_record")
public class EventRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp dateCreated;

    private Timestamp lastUpdated;

    @PreUpdate
    @PrePersist
    public void updateTimestamp() {
        lastUpdated = new Timestamp(System.currentTimeMillis());
        if (dateCreated == null) {
            dateCreated = new Timestamp(System.currentTimeMillis());
        }
    }

    private String topic;

    @Column(unique = true, nullable = false)
    private String payload;
}
