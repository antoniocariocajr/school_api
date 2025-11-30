package com.school.services.impl;

import com.school.persistence.entities.Notification;
import com.school.persistence.entities.Person;
import com.school.persistence.repositories.NotificationRepository;
import com.school.services.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    //private final JavaMailSender mailSender;
    //private final SmsProvider smsProvider;        // interface – Twilio, AWS SNS...
    //private final FirebaseMessaging firebaseMessaging;

    /* entrada síncrona – grava na fila */
    @Transactional
    @Override
    public Notification schedule(Person recipient,
                                 String channel,
                                 String destination,
                                 String subject,
                                 String content) {

        Notification n = Notification.builder()
                .recipient(recipient)
                .channel(channel)
                .destination(destination)
                .subject(subject)
                .content(content)
                .build();
        return notificationRepository.save(n);
    }

    /* job que roda a cada N segundos – envia notificações pendentes */
    @Async
    @Scheduled(fixedDelay = 30_000)
    @Override
    public void sendPending() {
        List<Notification> pending = notificationRepository.findByStatus(Notification.Status.PENDING);
        pending.forEach(this::dispatch);
    }

    private void dispatch(Notification n) {
        try {
            /*switch (n.getChannel()) {
                case "EMAIL" -> sendEmail(n);
                case "SMS"   -> sendSms(n);
                case "PUSH"  -> sendPush(n);
                default -> throw new IllegalArgumentException("Channel unknown");
            }*/
            n.setStatus(Notification.Status.SENT);
            n.setSentAt(Instant.now());
        } catch (Exception e) {
            log.error("Falha ao enviar notificação {}", n.getId(), e);
            n.setStatus(Notification.Status.FAILED);
            n.setErrorMessage(e.getMessage());
        }
        notificationRepository.save(n);
    }

    /* ---------- implementações dos canais ----------
    private void sendEmail(Notification n) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(n.getDestination());
        msg.setSubject(n.getSubject());
        msg.setText(n.getContent());
        mailSender.send(msg);
    }

    private void sendSms(Notification n) {
        smsProvider.send(n.getDestination(), n.getContent());
    }

    private void sendPush(Notification n) {
        Message msg = Message.builder()
                .setToken(n.getDestination())
                .setNotification(new Notification(n.getSubject(), n.getContent()))
                .build();
        firebaseMessaging.send(msg);
    }

    notificationService.schedule(
        responsible,
        "EMAIL",
        responsible.getEmail(),
        "Falta não justificada",
        "Seu filho faltou hoje na disciplina X.");*/
}
