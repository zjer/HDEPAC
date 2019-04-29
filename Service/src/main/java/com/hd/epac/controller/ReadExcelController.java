package com.hd.epac.controller;

import com.hd.epac.entity.Coin;
import com.hd.epac.util.ResultUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/getExcelData")
public class ReadExcelController {
    /*
     * 获取牛眼行情币价
     * */
    @GetMapping(value = "/getCoin")
    @ResponseBody
    public ResultUtil getExcelCoinData() throws Exception {
        try {
            List<Coin> list = new ArrayList<Coin>();
            //InputStream inputStream = new FileInputStream("E:\\Web\\MyProject\\HDEPAC\\Scrapy\\scrapy\\scrapyCoinPrice\\scrapyCoinPrice\\spiders\\coin.xls");
            InputStream inputStream = new FileInputStream("D:\\Others\\HDEPAC\\Scrapy\\scrapy\\scrapyCoinPrice\\scrapyCoinPrice\\spiders\\coin.xls");
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            for (int i = 1; i <= xssfSheet.getLastRowNum(); i ++) {
                XSSFRow xssfRow = xssfSheet.getRow(1);
                XSSFCell coinname = xssfRow.getCell(0);
                XSSFCell coinprice = xssfRow.getCell(1);
                XSSFCell coinchange = xssfRow.getCell(2);
                XSSFCell cointrade = xssfRow.getCell(3);

                Coin coin = new Coin();
                coin.setCoinname(getCellValue(coinname));
                coin.setCoinprice(getCellValue(coinprice));
                coin.setCoinchange(getCellValue(coinchange));
                coin.setCointrade(getCellValue(cointrade));
                list.add(coin);
            }
            return ResultUtil.success("获取成功！", list);
        } catch (Exception excelCoinData) {
            excelCoinData.printStackTrace();
            return ResultUtil.error("读取失败！");
        }
    }

    private String getCellValue(XSSFCell xssfCell) {
        if (xssfCell != null) {
            return String.valueOf(xssfCell.getStringCellValue());
        } else
            return "0";
    }
}
