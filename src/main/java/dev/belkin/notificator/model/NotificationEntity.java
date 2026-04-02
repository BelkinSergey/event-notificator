package dev.belkin.notificator.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    Integer eventId;

    Integer ownerId;

    Integer changeUserId;

    List<Integer> users;

    String data;

    String status;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FieldChangeEntity> fieldChangeEntityList;

    public NotificationEntity() {
    }

    public NotificationEntity(Integer eventId, Integer ownerId, List<Integer> users, Integer changeUserId, String data, String status, List<FieldChangeEntity> fieldChangeEntityList) {
        this.eventId = eventId;
        this.ownerId = ownerId;
        this.users = users;
        this.changeUserId = changeUserId;
        this.data = data;
        this.status = status;
        this.fieldChangeEntityList = fieldChangeEntityList;
    }

    public Integer getId() {
        return id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public Integer getChangeUserId() {
        return changeUserId;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public String getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public List<FieldChangeEntity> getFieldChangeEntityList() {
        return fieldChangeEntityList;
    }
}
