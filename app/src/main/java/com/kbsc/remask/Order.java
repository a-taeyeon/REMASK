package com.kbsc.remask;

public class Order {
    private String order_id;
    private String user_id;
    private String prdt_id;
    private int totalPrice;
    private String name;
    private String phone;
    private String shipAddr1;
    private String shipAddr2;
    private String zipCode;
    private long orderDate;
    private String state;

    public Order(){}

    public Order(String order_id, String user_id, String prdt_id, int totalPrice, String name, String phone, String shipAddr1, String shipAddr2, String zipCode, long orderDate, String state) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.prdt_id = prdt_id;
        this.totalPrice = totalPrice;
        this.name = name;
        this.phone = phone;
        this.shipAddr1 = shipAddr1;
        this.shipAddr2 = shipAddr2;
        this.zipCode = zipCode;
        this.orderDate = orderDate;
        this.state = state;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPrdt_id() {
        return prdt_id;
    }

    public void setPrdt_id(String prdt_id) {
        this.prdt_id = prdt_id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShipAddr1() {
        return shipAddr1;
    }

    public void setShipAddr1(String shipAddr1) {
        this.shipAddr1 = shipAddr1;
    }

    public String getShipAddr2() {
        return shipAddr2;
    }

    public void setShipAddr2(String shipAddr2) {
        this.shipAddr2 = shipAddr2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id='" + order_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", prdt_id='" + prdt_id + '\'' +
                ", totalPrice=" + totalPrice +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", shipAddr1='" + shipAddr1 + '\'' +
                ", shipAddr2='" + shipAddr2 + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", orderDate=" + orderDate +
                ", state='" + state + '\'' +
                '}';
    }
}
