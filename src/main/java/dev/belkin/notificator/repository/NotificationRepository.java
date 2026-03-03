package dev.belkin.notificator.repository;

import dev.belkin.notificator.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {


    List<NotificationEntity> findByChangeUserId(Integer userId);


    @Query("""
            SELECT n FROM NotificationEntity n
            WHERE n.changeUserId = :userId
            AND n.status = :status
            AND (:idsList IS NULL OR n.id IN :idsList)
            """)
    List<NotificationEntity> findAllNotificationNotRead(@Param("userId") Integer userId,
                                                        @Param("idsList") List<Integer> notificationIds,
                                                        @Param("status") String status);


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("""
            UPDATE NotificationEntity n
            SET n.status = :status
            WHERE n.id IN :list
            """)
    void updateStatusBatch(@Param("list") List<Integer> list,
                           @Param("status") String status);
}
