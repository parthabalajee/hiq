package com.healthsim.model;

import java.util.Date;

public abstract class AbstractUserEventMessage  {
    public final int id;
    public final Date time;
    public final int index;

    public AbstractUserEventMessage(int id, Date time, int index) {
        this.id = id;
        this.time = time;
        this.index = index;
    }

}
