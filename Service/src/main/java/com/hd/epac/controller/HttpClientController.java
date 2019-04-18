package com.hd.epac.controller;

import com.hd.epac.service.HttpClient;
import com.hd.epac.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/httpclient")
public class HttpClientController {
    @Autowired
    private HttpClient httpClient;

    @RequestMapping(value = "/getNews")
    public ResultUtil GetNews() {
        String url = "https://api.jinse.com/v4/live/list?limit=5&reading=false&source=web";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Object client = httpClient.client(url, method, params);
        return ResultUtil.success("获取成功！", client);
    }

    @RequestMapping(value = "/getWeather")
    public ResultUtil GetWeather() {
        String url = "https://www.tianqiapi.com/api/?version=v1";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Object client = httpClient.client(url, method, params);
        return ResultUtil.success("获取成功！", client);
    }
}
