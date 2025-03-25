package com.example.prm392_group2_shoesordersystem.entity;

public class CategorySale {
    private int category_id;
    private String category_name;
    private int totalQuantity;

    public CategorySale() {
    }

    public CategorySale(int category_id, String category_name, int totalQuantity) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.totalQuantity = totalQuantity;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
