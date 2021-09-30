package com.kbsc.remask;

public class MaskRegister {
    private String user_id;
    private long date;
    private int state;

    public MaskRegister(){}

    public MaskRegister(String user_id, long date, int state) {
        this.user_id = user_id;
        this.date = date;
        this.state = state;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "MaskRegister{" +
                "user_id='" + user_id + '\'' +
                ", date=" + date +
                ", state=" + state +
                '}';
    }
}
