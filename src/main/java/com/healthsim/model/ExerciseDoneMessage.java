package com.healthsim.model;

import java.util.Date;

public class ExerciseDoneMessage extends AbstractUserEventMessage {
    public ExerciseDoneMessage(int id, Date time, int index) {
        super(id, time, index);
    }

    @Override
    public String toString() {
        return "ExerciseDoneMessage{" +
                "id=" + id +
                ", time=" + time +
                ", index=" + index +
                '}';
    }
}
