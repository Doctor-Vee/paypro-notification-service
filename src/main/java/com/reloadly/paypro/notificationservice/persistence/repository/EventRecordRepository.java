package com.reloadly.paypro.notificationservice.persistence.repository;

import com.reloadly.paypro.notificationservice.persistence.model.EventRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRecordRepository extends JpaRepository<EventRecord, Long> {

}
