package com.kbsc.remask;

public class MaskOrder {
    private String user_id;
    private int fabricCnt;
    private int ringCnt;
    private int wireCnt;
    private String method;
    private String reason;
    private long date;
    private String state;

    public MaskOrder(){}

    public MaskOrder(String user_id, int fabricCnt, int ringCnt, int wireCnt, String method, String reason, long date, String state) {
        this.user_id = user_id;
        this.fabricCnt = fabricCnt;
        this.ringCnt = ringCnt;
        this.wireCnt = wireCnt;
        this.method = method;
        this.reason = reason;
        this.date = date;
        this.state = state;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getFabricCnt() {
        return fabricCnt;
    }

    public void setFabricCnt(int fabricCnt) {
        this.fabricCnt = fabricCnt;
    }

    public int getRingCnt() {
        return ringCnt;
    }

    public void setRingCnt(int ringCnt) {
        this.ringCnt = ringCnt;
    }

    public int getWireCnt() {
        return wireCnt;
    }

    public void setWireCnt(int wireCnt) {
        this.wireCnt = wireCnt;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "MaskOrder{" +
                "user_id='" + user_id + '\'' +
                ", fabricCnt=" + fabricCnt +
                ", ringCnt=" + ringCnt +
                ", wireCnt=" + wireCnt +
                ", method='" + method + '\'' +
                ", reason='" + reason + '\'' +
                ", date=" + date +
                ", state='" + state + '\'' +
                '}';
    }
}
