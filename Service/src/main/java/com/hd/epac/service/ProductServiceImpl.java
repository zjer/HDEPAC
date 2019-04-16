package com.hd.epac.service;

import com.hd.epac.dao.ProductDao;
import com.hd.epac.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    /*
     * 添加产品
     * */
    @Override
    public int AddProduct(Integer productid, String productcode, String productname, Integer producttype, Timestamp createtime) {
        return productDao.addProduct(productid, productcode, productname, producttype, createtime);
    }

    /*
     * 获取所有产品
     * */
    @Override
    public List<Product> FindAllProducts() {
        List<Product> list = productDao.findAllProducts();
        return list;
    }
}
