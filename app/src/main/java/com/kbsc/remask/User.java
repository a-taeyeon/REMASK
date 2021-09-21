package com.kbsc.remask;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private String userName;
    private String email;
    private String password;
    private String phone;
    private boolean seller;
    private String sellerNum;

    public User(){}

    public User(String userName, String email, String password, String phone, boolean seller) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.seller = seller;
    }

    public User(String userName, String email, String password, String phone, boolean seller, String sellerNum) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.seller = seller;
        this.sellerNum = sellerNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSeller() {
        return seller;
    }

    public void setSeller(boolean seller) {
        this.seller = seller;
    }

    public String getSellerNum() {
        return sellerNum;
    }

    public void setSellerNum(String sellerNum) {
        this.sellerNum = sellerNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", seller=" + seller +
                ", sellerNum='" + sellerNum + '\'' +
                '}';
    }
}
