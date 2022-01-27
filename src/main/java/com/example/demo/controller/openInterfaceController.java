package com.example.demo.controller;

import cn.hutool.http.HttpUtil;
import com.example.demo.dao.ToolDao;
import com.example.demo.mod.ToolMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 开放接口
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Open")
public class openInterfaceController {
    @Autowired
    private ToolDao toolDao;
    ToolMod toolMod = new ToolMod();
    /**
     *GET跨域请求
     */
    @PostMapping("getHttp")
    public String getHttp(@RequestParam String url){
        String results = null;
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("url",url);
            param.put("type","GET");
            param.put("time",toolMod.time());
            results = HttpUtil.get(url);
            if (results.length()>1000){
                param.put("results",results.substring(0,999));
            }else {
                param.put("results",results);
            }
            toolDao.domainUrlCode(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    /**
     *POST跨域请求
     */
    @PostMapping("postHttp")
    public String postHttp(@RequestParam String url,@RequestParam String value){
        String results = null;
        try{
            Map<String,Object> param = new HashMap<>();
            param.put("url",url);
            param.put("value",value);
            param.put("type","POST");
            param.put("time",toolMod.time());
            results = HttpUtil.post(url,value);
            if (results.length()>1000){
                param.put("results",results.substring(0,999));
            }else {
                param.put("results",results);
            }
            toolDao.domainUrlCode(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
}
