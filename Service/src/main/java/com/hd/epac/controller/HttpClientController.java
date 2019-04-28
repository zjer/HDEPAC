package com.hd.epac.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hd.epac.service.HttpClient;
import com.hd.epac.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/httpclient")
public class HttpClientController {
    @Autowired
    private HttpClient httpClient;

    /*
     * 获取金色财经快讯
     * */
    @GetMapping(value = "/getNews")
    @ResponseBody
    public ResultUtil GetNews() {
        String url = "https://api.jinse.com/v4/live/list?limit=10&reading=false&source=web";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Object client = httpClient.client(url, method, params);
        JSONObject jsonObject = JSON.parseObject((String) client);
        return ResultUtil.success("获取成功！", jsonObject);
    }

    /*
     * 获取一周天气
     * */
    @GetMapping(value = "/getWeather")
    @ResponseBody
    public ResultUtil GetWeather() {
        String url = "https://www.tianqiapi.com/api/?version=v1";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Object client = httpClient.client(url, method, params);
        JSONObject jsonObject = JSON.parseObject((String) client);
        return ResultUtil.success("获取成功！", jsonObject);
    }

    /*
     * 获取牛眼行情币价
     * */
    @GetMapping(value = "/getCoinPrice")
    @ResponseBody
    public ResultUtil getCoinPrice() {
        String url = "https://fastmarket.niuyan.com/api/v2/web/coins?page=1&pagesize=10&lan=zh-cn";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Object client = httpClient.client(url, method, params);
        JSONObject jsonObject = JSON.parseObject((String) client);
        return ResultUtil.success("获取成功！", jsonObject);
    }

    /*
     * 获取牛眼行情币价(实时)
     * */
    @GetMapping(value = "/getNowPrice")
    @ResponseBody
    public ResultUtil getNowPrice() {
        String url = "https://fastmarket.niuyan.com/api/v2/common/financerate?lan=zh-cn";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Object client = httpClient.client(url, method, params);
        JSONObject jsonObject = JSON.parseObject((String) client);
        return ResultUtil.success("获取成功！", jsonObject);
    }
}
