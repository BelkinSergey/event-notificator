package dev.belkin.notificator.service;


import dev.belkin.notificator.dto.NotificationDto;
import dev.belkin.notificator.model.NotificationEntity;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getAllUnreadNotificationByOwner();

    void createNotification(NotificationDto dto);


    List<Integer> changeStatusNotification();

}
