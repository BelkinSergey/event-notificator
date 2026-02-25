package dev.belkin.notificator.mapper;


import dev.belkin.notificator.dto.EventChangeNotificationDto;
import dev.belkin.notificator.dto.NotificationDto;
import dev.belkin.notificator.model.FieldChangeEntity;
import dev.belkin.notificator.model.NotificationEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationMapper {

private final FieldChangeNotificationMapper fieldChangeNotificationMapper;

    public NotificationMapper(FieldChangeNotificationMapper fieldChangeNotificationMapper) {
        this.fieldChangeNotificationMapper = fieldChangeNotificationMapper;
    }

    public NotificationEntity toNotificationEntityFromDto(NotificationDto dto ){

        return  new NotificationEntity(
                dto.eventId(),
                dto.ownerId(),
                dto.usersId(),
                dto.changeUserId(),
                dto.createdAt(),
                dto.notificationStatus(),
                new ArrayList<>()
        );
    }

    public NotificationDto notificationDtoFromEntity(NotificationEntity entity){

        return new NotificationDto(
                entity.getEventId(),
                entity.getChangeUserId(),
                entity.getOwnerId(),
                entity.getData(),
                entity.getStatus(),
                getList(entity.getFieldChangeEntityList()),
                entity.getUsers()
        );
    }

    private List<EventChangeNotificationDto<?>> getList(List<FieldChangeEntity> list){

      return   list.stream()
                .map(fieldChangeNotificationMapper::toDtoFromEntity)
                .collect(Collectors.toList());
    }
}
