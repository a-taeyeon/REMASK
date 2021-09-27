package com.kbsc.remask;

public class MyMask {
    private long date;
    private int fabricCnt;
    private int ringCnt;
    private int wireCnt;
    private String result;

    public MyMask(){}

    public MyMask(long date, int fabricCnt, int ringCnt, int wireCnt, String result) {
        this.date = date;
        this.fabricCnt = fabricCnt;
        this.ringCnt = ringCnt;
        this.wireCnt = wireCnt;
        this.result = result;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
