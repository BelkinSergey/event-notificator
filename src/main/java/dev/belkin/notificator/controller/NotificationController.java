package dev.belkin.notificator.controller;

import dev.belkin.notificator.dto.NotificationDto;
import dev.belkin.notificator.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllUnreadNotificationByOwner() {
        log.info("получен запрос на получение всех нотификация пользователья");

        List<NotificationDto> notificationDtoList = notificationService.getAllUnreadNotificationByOwner();

        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationDtoList);
    }

    @PostMapping()
    public ResponseEntity<List<Integer>> changeStatusNotification(@RequestBody List<Integer> notificationIds) {
        log.info("получен запрос на изменение статуса нотификации");

        List<Integer> notificationId = notificationService.changeStatusNotification(notificationIds);

        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationId);
    }

}
