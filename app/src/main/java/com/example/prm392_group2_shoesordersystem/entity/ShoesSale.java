package com.example.prm392_group2_shoesordersystem.entity;

public class ShoesSale {
    private int shoes_id;
    private String shoes_name;
    private String img;
    private int item_bought;
    private double total_amount;

    public ShoesSale() {
    }

    public ShoesSale(int shoes_id, double total_amount, int item_bought, String img, String shoes_name) {
        this.shoes_id = shoes_id;
        this.total_amount = total_amount;
        this.item_bought = item_bought;
        this.img = img;
        this.shoes_name = shoes_name;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public int getItem_bought() {
        return item_bought;
    }

    public void setItem_bought(int item_bought) {
        this.item_bought = item_bought;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShoes_name() {
        return shoes_name;
    }

    public void setShoes_name(String shoes_name) {
        this.shoes_name = shoes_name;
    }

    public int getShoes_id() {
        return shoes_id;
    }

    public void setShoes_id(int shoes_id) {
        this.shoes_id = shoes_id;
    }
}
