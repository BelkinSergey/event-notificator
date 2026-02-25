package dev.belkin.notificator.service;

import dev.belkin.notificator.NotificationStatus;
import dev.belkin.notificator.controller.NotificationController;
import dev.belkin.notificator.dto.EventChangeNotificationDto;
import dev.belkin.notificator.dto.NotificationDto;
import dev.belkin.notificator.mapper.NotificationMapper;
import dev.belkin.notificator.model.NotificationEntity;
import dev.belkin.notificator.repository.NotificationRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {


    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final FieldChangeService fieldChangeService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper notificationMapper, FieldChangeService fieldChangeService) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.fieldChangeService = fieldChangeService;
    }


    @Override
    public List<NotificationDto> getAllUnreadNotificationByOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId;
        if (authentication == null) {
            throw new IllegalStateException("аутентификация не найдена");
        }
        userId = (Integer) authentication.getPrincipal();
        List<NotificationEntity> notificationEntityList = notificationRepository.findByChangeUserId(userId);
        return notificationEntityList.stream()
                .map(notificationMapper::notificationDtoFromEntity)
                .toList();
    }

    @Override
    public void createNotification(@Valid NotificationDto dto) {
        List<EventChangeNotificationDto<?>> changesList = dto.changes();
        NotificationEntity notification = notificationMapper.toNotificationEntityFromDto(dto);
        changesList.forEach(eventChangeNotificationDto ->
                notification.getFieldChangeEntityList()
                        .add(fieldChangeService.createChanges(notification, eventChangeNotificationDto)));

        notificationRepository.save(notification);
    }

    @Override
    public List<Integer> changeStatusNotification() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId;

        if (authentication == null) {
            throw new IllegalStateException("аутентификация не найдена");
        }
        userId = (Integer) authentication.getPrincipal();

        List<NotificationEntity> entities = notificationRepository.findAllNotificationNotRead(userId,
                NotificationStatus.UNREAD.toString());

        List<Integer> idsList = entities.stream()
                .map(NotificationEntity::getId)
                .toList();

        notificationRepository.updateStatusBatch(idsList, NotificationStatus.READ.toString());

        return idsList;
    }


}
