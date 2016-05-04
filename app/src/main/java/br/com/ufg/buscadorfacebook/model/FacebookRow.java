package br.com.ufg.buscadorfacebook.model;

import java.util.ArrayList;

public class FacebookRow {
    public String category;
    public ArrayList<CategoryList> category_list;
    public Location location;
    public String name;
    public String id;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<CategoryList> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(ArrayList<CategoryList> category_list) {
        this.category_list = category_list;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

