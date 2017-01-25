package com.company;

import java.io.Serializable;

/**
 * Created by Dzmitry_Sankouski on 20-Jan-17.
 */
public class SportEquipment implements Serializable{

    private Category category;
    private String title;
    private String condition;
    private int price;

    public SportEquipment(Category category, String title, String condition, int price) {
        this.category = category;
        this.title = title;
        this.condition = condition;
        this.price = price;
    }

    public SportEquipment(Category category, String title, int price) {
        this.category = category;
        this.title = title;
        this.price = price;
    }

    public SportEquipment(String title) {
        this.title = title;
    }


        //----------------------------------getters & setters
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
