package com.hd.epac.service;

import com.hd.epac.dao.ProductDao;
import com.hd.epac.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    /*
     * 添加产品
     * */
    @Override
    public int AddProduct(Integer productid, String productcode, String productname, Integer producttype, Integer state) {
        return productDao.addProduct(productid, productcode, productname, producttype, state);
    }

    /*
     * 更新产品信息
     * */
    @Override
    public int UpdateProduct(Integer productid, String productcode, String productname, Integer producttype, Integer state) {
        return productDao.updateProduct(productid, productcode, productname, producttype, state);
    }

    /*
     * 获取所有产品
     * */
    @Override
    public List<Product> FindAllProducts() {
        List<Product> list = productDao.findAllProducts();
        return list;
    }

    /*
     * 删除产品（单删）
     * */
    @Override
    public int DelProduct(Integer productid) {
        return productDao.delProduct(productid);
    }

    /*
     * 删除产品（多删）
     * */
    @Override
    public int DelProducts(String idLists) {
        String arr[] = idLists.split(",");
        List list = new ArrayList();
        for (int i = 0; i < arr.length; i ++) {
            list.add(Integer.parseInt(arr[i]));
        }
        return productDao.delProducts(list);
    }

    /*
     * 更改产品状态
     * */
    @Override
    public int UpdateState(Integer productid, Integer state) {
        return productDao.updateState(productid, state);
    }
}
