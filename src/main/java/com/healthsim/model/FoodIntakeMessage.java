package com.healthsim.model;

import java.util.Date;

public class FoodIntakeMessage extends AbstractUserEventMessage{
    public FoodIntakeMessage(int id, Date time, int index) {
        super(id, time, index);
    }

    @Override
    public String toString() {
        return "FoodIntakeMessage{" +
                "id=" + id +
                ", time=" + time +
                ", index=" + index +
                '}';
    }
}
