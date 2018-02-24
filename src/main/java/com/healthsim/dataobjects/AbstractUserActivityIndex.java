package com.healthsim.dataobjects;

public class AbstractUserActivityIndex {

    private final Integer id;
    private final String name;
    private final Integer index;

    public AbstractUserActivityIndex(Integer id, String name, Integer index) {
        this.id = id;
        this.name = name;
        this.index = index;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "AbstractUserActivityIndex{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", index=" + index +
                '}';
    }
}
