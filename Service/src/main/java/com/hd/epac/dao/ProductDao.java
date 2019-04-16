package com.hd.epac.dao;

import com.hd.epac.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ProductDao {
    int deleteByPrimaryKey(Integer productid);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer productid);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    //新增产品
    public int addProduct(@Param("productid") Integer productid, @Param("productcode") String productcode, @Param("productname") String productname, @Param("producttype") Integer producttype, @Param("createtime") Timestamp createtime, @Param("state") Integer state);


    //获取所有用户
    public List<Product> findAllProducts();
}