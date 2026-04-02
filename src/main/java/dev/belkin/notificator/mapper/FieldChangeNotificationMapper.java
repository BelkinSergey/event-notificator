package dev.belkin.notificator.mapper;


import dev.belkin.notificator.dto.EventChangeNotificationDto;
import dev.belkin.notificator.model.FieldChangeEntity;
import dev.belkin.notificator.model.NotificationEntity;
import org.springframework.stereotype.Component;

@Component
public class FieldChangeNotificationMapper {

    public FieldChangeEntity toEntityFromDto(
            NotificationEntity notification,
            EventChangeNotificationDto<?> dto
    ) {
        return new FieldChangeEntity(
                notification,
                dto.getName(),
                dto.getOldField() == null ? null : dto.getOldField().toString(),
                dto.getNewField() == null ? null : dto.getNewField().toString()
                );
    }

    public EventChangeNotificationDto<?> toDtoFromEntity(FieldChangeEntity entity){

        return new EventChangeNotificationDto<>(
               entity.getFieldName(),
               entity.getOldValue(),
               entity.getNewValue()
        );
    }

}
