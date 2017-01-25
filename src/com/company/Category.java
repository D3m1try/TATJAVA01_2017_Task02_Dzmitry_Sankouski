package com.company;

import java.io.Serializable;

/**
 * Created by Dzmitry_Sankouski on 20-Jan-17.
 */
public class Category implements Serializable{
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }


    //--------------------------------------getters & setters
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
