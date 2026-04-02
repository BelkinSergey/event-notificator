package dev.belkin.notificator.dto;


import dev.belkin.notificator.NotificationStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;


public record NotificationDto(

        @NotNull
        @Min(1)
        Integer eventId,

        Integer changeUserId,

        @NotNull
        @Min(1)
        Integer ownerId,

        String createdAt,

        @Pattern(regexp = "READ|UNREAD", message = "Статус должен быть READ или UNREAD")
        String notificationStatus,

        List<EventChangeNotificationDto<?>> changes,

        List<Integer> usersId


) {
}
