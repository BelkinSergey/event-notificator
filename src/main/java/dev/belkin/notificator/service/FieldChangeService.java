package dev.belkin.notificator.service;

import dev.belkin.notificator.dto.EventChangeNotificationDto;
import dev.belkin.notificator.model.FieldChangeEntity;
import dev.belkin.notificator.model.NotificationEntity;

public interface FieldChangeService {

    FieldChangeEntity createChanges(NotificationEntity notificationEntity,
                                    EventChangeNotificationDto<?> eventChangeNotificationDto);
}
