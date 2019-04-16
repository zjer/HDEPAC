package com.hd.epac.entity;

import java.sql.Timestamp;
import java.util.Date;

public class Product {
    private Integer productid;

    private String productcode;

    private String productname;

    private Integer producttype;

    private Timestamp createtime;

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode == null ? null : productcode.trim();
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public Integer getProducttype() {
        return producttype;
    }

    public void setProducttype(Integer producttype) {
        this.producttype = producttype;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productid=" + productid +
                ", productcode='" + productcode + '\'' +
                ", productname='" + productname + '\'' +
                ", producttype=" + producttype +
                ", createtime=" + createtime +
                '}';
    }
}