package dev.belkin.notificator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "field_changes")
public class FieldChangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    NotificationEntity notification;

    String fieldName;

    String oldValue;

    String newValue;


    public FieldChangeEntity() {
    }

    public FieldChangeEntity(NotificationEntity notification, String fieldName, String oldValue, String newValue) {
        this.notification = notification;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Integer getId() {
        return id;
    }

    public NotificationEntity getNotification() {
        return notification;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }
}
