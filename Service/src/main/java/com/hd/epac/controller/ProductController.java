package com.hd.epac.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hd.epac.entity.Product;
import com.hd.epac.service.ProductService;
import com.hd.epac.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /*
     * 添加用户
     * */
    @PostMapping(value = "/addProduct")
    @ResponseBody
    public ResultUtil AddProduct(@RequestParam(value = "productid", required = false) Integer productid, @RequestParam(value = "productcode", required = false) String productcode, @RequestParam(value = "productname", required = false) String productname, @RequestParam(value = "producttype", required = false) Integer producttype, @RequestParam(value = "createtime", required = false) Timestamp createtime) {
        System.out.println(productid + ',' + productcode + ',' + productname + ',' + producttype + ',' + createtime);
        Object product = productService.AddProduct(productid, productcode, productname, producttype, createtime);
        return ResultUtil.success("添加成功！", product);
    }

    /*
     * 获取所有用户
     * */
    @PostMapping(value = "/getProducts")
    @ResponseBody
    public ResultUtil FindAllProducts(@RequestParam(value = "pageIndex", required = false) Integer pageIndex, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Product> list = productService.FindAllProducts();
        PageInfo pageInfo = new PageInfo(list);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("list", pageInfo.getList());
        System.out.println(map);
        return ResultUtil.success("查询成功！", map);
    }
}
