package dev.belkin.notificator.service;

import dev.belkin.notificator.dto.EventChangeNotificationDto;
import dev.belkin.notificator.mapper.FieldChangeNotificationMapper;
import dev.belkin.notificator.model.FieldChangeEntity;
import dev.belkin.notificator.model.NotificationEntity;
import dev.belkin.notificator.repository.FieldChangeRepository;
import org.springframework.stereotype.Service;

@Service
public class FieldChangeServiceImpl implements FieldChangeService{

    private final FieldChangeRepository fieldChangeRepository;
    private final FieldChangeNotificationMapper fieldChangeNotificationMapper;


    public FieldChangeServiceImpl(FieldChangeRepository fieldChangeRepository, FieldChangeNotificationMapper fieldChangeNotificationMapper) {
        this.fieldChangeRepository = fieldChangeRepository;
        this.fieldChangeNotificationMapper = fieldChangeNotificationMapper;
    }


    @Override
    public FieldChangeEntity createChanges(NotificationEntity notificationEntity,
                                           EventChangeNotificationDto<?> eventChangeNotificationDto) {

    return fieldChangeNotificationMapper
                .toEntityFromDto(notificationEntity, eventChangeNotificationDto);
    }
}
