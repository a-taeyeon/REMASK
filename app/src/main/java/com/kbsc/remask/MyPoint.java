package com.kbsc.remask;

public class MyPoint {
    private int point_id;
    private String user_id;
    private String content;
    private String date;
    private int value;

    public MyPoint(){}

    public MyPoint(String user_id, String content, String date, int value) {
        this.user_id = user_id;
        this.content = content;
        this.date = date;
        this.value = value;
    }

    public MyPoint(int point_id, String user_id, String content, String date, int value) {
        this.point_id = point_id;
        this.user_id = user_id;
        this.content = content;
        this.date = date;
        this.value = value;
    }

    public int getPoint_id() {
        return point_id;
    }

    public void setPoint_id(int point_id) {
        this.point_id = point_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyPoint{" +
                "point_id='" + point_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", value=" + value +
                '}';
    }
}
