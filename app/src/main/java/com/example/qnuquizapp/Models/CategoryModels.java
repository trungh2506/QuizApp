package com.example.qnuquizapp.Models;

import java.io.Serializable;

public class CategoryModels implements Serializable {
    private String categoryName;
    public CategoryModels(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
