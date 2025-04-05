package com.example.myevent_be.repository;

import com.example.myevent_be.entity.EmailSendLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSendLogRepository extends JpaRepository<EmailSendLog, String> {
}
