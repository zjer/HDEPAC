package com.hd.epac.dao;

import com.hd.epac.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    int deleteByPrimaryKey(Integer productid);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer productid);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    //新增产品
    public int addProduct(@Param("productid") Integer productid, @Param("productcode") String productcode, @Param("productname") String productname, @Param("producttype") Integer producttype, @Param("state") Integer state);

    //更新产品
    public int updateProduct(@Param("productid") Integer productid, @Param("productcode") String productcode, @Param("productname") String productname, @Param("producttype") Integer producttype, @Param("state") Integer state);

    //获取所有产品
    public List<Product> findAllProducts();

    //删除产品
    public int delProduct(@Param("productid") Integer productid);

    //批量删除产品
    public int delProducts(@Param("list") List list);

    //更改产品状态
    public int updateState(@Param("productid") Integer productid, @Param("state") Integer state);
}