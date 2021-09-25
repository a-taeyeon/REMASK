package com.kbsc.remask;

public class ProductImages {
    private int prdtId;
    private String prdtName;

    public ProductImages(){}

    public ProductImages(int prdtId, String prdtName)
    {
        this.prdtId = prdtId;
        this.prdtName = prdtName;
    }

    public int getPrdtId()
    {
        return prdtId;
    }

    public void setPrdtId(int PrdtId)
    {
        this.prdtId = prdtId;
    }

    public String getPrdtName()
    {
        return prdtName;
    }

    public void setPrdtName(String prdtName)
    {
        this.prdtName = prdtName;
    }
}