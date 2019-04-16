package com.hd.epac.service;

import com.hd.epac.entity.Product;

import java.sql.Timestamp;
import java.util.List;

public interface ProductService {
    //新增产品
    public int AddProduct(Integer productid, String productcode, String productname, Integer producttype, Timestamp createtime, Integer state);


    //获取所有用户
    public List<Product> FindAllProducts();
}
