package com.kbsc.remask;

public class MyOrder {
    //(추가) 이미지 관련 변수

    private String order_id;
    private String prdtName;
    private String state;

    public MyOrder(){}

    public MyOrder(String order_id, String prdtName, String state) {
        this.order_id = order_id;
        this.prdtName = prdtName;
        this.state = state;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPrdtName() {
        return prdtName;
    }

    public void setPrdtName(String prdtName) {
        this.prdtName = prdtName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
