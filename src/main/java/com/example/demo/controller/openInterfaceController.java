package com.example.demo.controller;

import cn.hutool.http.HttpUtil;
import com.example.demo.body.PostHttpBody;
import com.example.demo.dao.ToolDao;
import com.example.demo.mod.ToolMod;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("getHttp")
    public String getHttp(@RequestParam String url){
        try{
            return HttpUtil.get(url);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     *POST跨域请求
     */
    @PostMapping("postHttp")
    public String postHttp(@RequestBody PostHttpBody postHttpBody){
        String results = null;
        try{
            if (postHttpBody.getUrl() != null){
                String value = "";
                if (postHttpBody.getValue() != null)
                    for (Map.Entry<String, Object> entry : postHttpBody.getValue().entrySet()) {
                        value +=(entry.getKey()+"="+entry.getValue()+"&");
                    }
                if ("".equals(value)) {
                    value = value.substring(0,value.length()-1);
                }
                results = HttpUtil.post(postHttpBody.getUrl(),value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
}
