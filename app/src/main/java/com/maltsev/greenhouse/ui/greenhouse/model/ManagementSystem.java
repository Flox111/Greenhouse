package com.maltsev.greenhouse.ui.greenhouse.model;

public class ManagementSystem {
    private long id;
    private String name;
    private TypeManagementSystem type;
    private String icon;

    public ManagementSystem(String name, TypeManagementSystem type) {
        this.name = name;
        this.type = type;
    }

    public ManagementSystem(String name, TypeManagementSystem type, String icon) {
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeManagementSystem getType() {
        return type;
    }

    public void setType(TypeManagementSystem type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
