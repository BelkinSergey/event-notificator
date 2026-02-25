package dev.belkin.notificator.kafka;

import dev.belkin.notificator.dto.NotificationDto;
import dev.belkin.notificator.service.NotificationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class EventKafkaListener {

    private static final Logger log = LoggerFactory.getLogger(EventKafkaListener.class);
    private final NotificationService notificationService;

    public EventKafkaListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "events-topic", groupId = "notificator-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenEvents(
            ConsumerRecord<Integer, NotificationDto> record
    ) {
        log.info("get event: event={}", record.value());

        NotificationDto dto = record.value();

        notificationService.createNotification(dto);
    }
}
