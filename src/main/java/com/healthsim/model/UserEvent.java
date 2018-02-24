package com.healthsim.model;

import java.util.Date;

public class UserEvent {
    public final int userID;
    public final int id;
    public final Date time;
    public final EventType eventType;
    public enum EventType { EXERCISE,FOOD};

    public UserEvent(int userID,int id, Date time,EventType eventType) {
        this.userID = userID;
        this.id = id;
        this.time = time;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "id=" + id +
                ", time=" + time +
                ", eventType=" + eventType +
                '}';
    }
}
