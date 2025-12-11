package com.sakila.data;

import java.util.Date;

public class Category extends Entity {

    private int categoryId;
    private String name;
    private Date lastUpdate;

    public Category() {
    }

    public Category(int categoryId, String name, Date lastUpdate) {
        this.categoryId = categoryId;
        this.name = name;
        this.lastUpdate = lastUpdate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return categoryId + " | " + name;
    }
}