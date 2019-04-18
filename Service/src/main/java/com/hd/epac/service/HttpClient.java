package com.hd.epac.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpClient {
    public Object client(String url, HttpMethod method, MultiValueMap<String, String> params) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
        JSONObject jsonObject = JSON.parseObject((String) responseEntity.getBody());
        return jsonObject;
    }
}
