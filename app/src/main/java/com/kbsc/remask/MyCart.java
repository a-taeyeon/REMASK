package com.kbsc.remask;

import java.io.Serializable;

public class MyCart implements Serializable {

    private String prdtName;
    private String prdtAbbr;
    private int price;
    private boolean isSelected;

    public MyCart(){}
    public MyCart(String prdtName, String prdtAbbr, int price) {
        this.prdtName = prdtName;
        this.prdtAbbr = prdtAbbr;
        this.price = price;
    }
    public MyCart(String prdtName, String prdtAbbr, int price, boolean isSelected) {
        this.prdtName = prdtName;
        this.prdtAbbr = prdtAbbr;
        this.price = price;
        this.isSelected = isSelected;
    }

    public String getPrdtName() {
        return prdtName;
    }

    public void setPrdtName(String prdtName) {
        this.prdtName = prdtName;
    }

    public String getPrdtAbbr() {
        return prdtAbbr;
    }

    public void setPrdtAbbr(String prdtAbbr) {
        this.prdtAbbr = prdtAbbr;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
