package com.shivasnape.samplemvvm.model;

public class CommonModel {

    private int id;
    private String name;

    public CommonModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CommonModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
