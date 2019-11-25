package com.alirnp.salizsalon.ADMIN.Sticky;

public class Model {
    private String title;
    private Integer type;
    private boolean reserved;


    public Model() {

    }

    public Model(String name, Integer type) {
        this.title = name;
        this.type = type;
    }

    public Model(String name, Integer type, boolean reserved) {
        this.title = name;
        this.type = type;
        this.reserved = reserved;
    }


    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}