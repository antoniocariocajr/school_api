package com.school.services;

import com.school.persistence.entities.Notification;
import com.school.persistence.entities.Person;

public interface NotificationService {
    Notification schedule(Person recipient,
                          String channel,
                          String destination,
                          String subject,
                          String content);
    void sendPending();
}
