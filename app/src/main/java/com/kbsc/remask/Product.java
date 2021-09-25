package com.kbsc.remask;

public class Product {
    private String user_id;
    private String prdtName;
    private String prdtAbbr;
    private String prdtDetail;
    private int point;
    private int stock;
    private String post_id;
    private long post_date;

    public Product(){}

    public Product(String user_id, String prdtName, String prdtAbbr, String prdtDetail, int point, int stock, String post_id, long post_date) {
        this.user_id = user_id;
        this.prdtName = prdtName;
        this.prdtAbbr = prdtAbbr;
        this.prdtDetail = prdtDetail;
        this.point = point;
        this.stock = stock;
        this.post_id = post_id;
        this.post_date = post_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getPrdtDetail() {
        return prdtDetail;
    }

    public void setPrdtDetail(String prdtDetail) {
        this.prdtDetail = prdtDetail;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public long getPost_date() {
        return post_date;
    }

    public void setPost_date(long post_date) {
        this.post_date = post_date;
    }

    @Override
    public String toString() {
        return "Product{" +
                "user_id='" + user_id + '\'' +
                ", prdtName='" + prdtName + '\'' +
                ", prdtAbbr='" + prdtAbbr + '\'' +
                ", prdtDetail='" + prdtDetail + '\'' +
                ", point=" + point +
                ", stock=" + stock +
                ", post_id='" + post_id + '\'' +
                ", post_date=" + post_date +
                '}';
    }
}