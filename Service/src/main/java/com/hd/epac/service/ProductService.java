package com.hd.epac.service;

import com.hd.epac.entity.Product;

import java.util.List;

public interface ProductService {
    //新增产品
    public int AddProduct(Integer productid, String productcode, String productname, Integer producttype, Integer state);

    //更新产品
    public int UpdateProduct(Integer productid, String productcode, String productname, Integer producttype, Integer state);

    //获取所有产品
    public List<Product> FindAllProducts();

    //删除产品
    public int DelProduct(Integer productid);

    //批量删除产品
    public int DelProducts(String idLists);

    //更改产品状态
    public int UpdateState(Integer productid, Integer state);
}
