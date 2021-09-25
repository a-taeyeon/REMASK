package com.kbsc.remask;

public class Cart {
    private String user_id;
    private String product_id;

    public Cart(){}

    public Cart(String user_id, String product_id) {
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "MyCart{" +
                "user_id='" + user_id + '\'' +
                ", product_id='" + product_id + '\'' +
                '}';
    }
}
