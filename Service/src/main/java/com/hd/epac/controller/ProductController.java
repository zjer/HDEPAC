package com.hd.epac.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hd.epac.entity.Product;
import com.hd.epac.service.ProductService;
import com.hd.epac.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /*
     * 添加产品
     * */
    @PostMapping(value = "/addProduct")
    @ResponseBody
    public ResultUtil AddProduct(@RequestParam(value = "productid", required = false) Integer productid, @RequestParam(value = "productcode", required = false) String productcode, @RequestParam(value = "productname", required = false) String productname, @RequestParam(value = "producttype", required = false) Integer producttype, @RequestParam(value = "state", required = false) Integer state) {
        System.out.println(productid + ',' + productcode + ',' + productname + ',' + producttype + ',' + state);
        Object product = productService.AddProduct(productid, productcode, productname, producttype, state);
        return ResultUtil.success("添加成功！", product);
    }

    /*
     * 更新产品信息
     * */
    @PostMapping(value = "/updateProduct")
    @ResponseBody
    public ResultUtil UpdateProduct(@RequestParam(value = "productid", required = false) Integer productid, @RequestParam(value = "productcode", required = false) String productcode, @RequestParam(value = "productname", required = false) String productname, @RequestParam(value = "producttype", required = false) Integer producttype, @RequestParam(value = "state", required = false) Integer state) {
        System.out.println(productid + ',' + productcode + ',' + productname + ',' + producttype + state);
        Object product = productService.UpdateProduct(productid, productcode, productname, producttype, state);
        return ResultUtil.success("修改成功！", product);
    }

    /*
     * 获取所有产品
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

    /*
     * 删除产品（单删）
     * */
    @PostMapping(value = "/delProduct")
    @ResponseBody
    public ResultUtil DelProduct(@RequestParam(value = "productid", required = false) Integer productid) {
        System.out.println(productid);
        Object product = productService.DelProduct(productid);
        return ResultUtil.success("删除成功！", product);
    }

    /*
     * 删除产品（多删）
     * */
    @PostMapping(value = "/delProducts")
    @ResponseBody
    public ResultUtil DelProducts(@RequestParam(value = "idLists", required = false) String idLists) {
        System.out.println(idLists);
        Object product = productService.DelProducts(idLists);
        return ResultUtil.success("删除成功！", product);
    }

    /*
     * 更改产品状态
     * */
    @PostMapping(value = "/updateState")
    @ResponseBody
    public ResultUtil UpdateState(@RequestParam(value = "productid", required = false) Integer productid, @RequestParam(value = "state", required = false) Integer state) {
        System.out.println(productid + ',' + state);
        Object product = productService.UpdateState(productid, state);
        return ResultUtil.success("状态修改成功！", product);
    }
}
