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

    /**
     *土豆任务（临时）
     */
    @GetMapping("tuDou")
    public void tuDou(@RequestParam int start,@RequestParam int end){
        int num = 0;
        try{
            JSONObject jsonObj = null;
            Map<String,Object> param = new HashMap<>();
            for(int i=start;i<=end;i++){
                num=i;
                jsonObj = new JSONObject(HttpUtil.post("http://tdadmin.tdsdyxly.com/api/tudou/kamilogin","action=vipyanz&uid="+i));
                if (!jsonObj.get("message").equals("您还无法查看黄金会员专区资源，请联系客服")){
                    param.put("uid",i);
                    param.put("message",jsonObj.get("message"));
                    toolDao.tuDouCode(param);
                }
            }
            param.put("name","土豆任务");
            param.put("information","任务完成："+start+"-"+end);
            param.put("time",toolMod.time());
            toolDao.taskInformation(param);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,Object> para = new HashMap<>();
            para.put("name","土豆任务");
            para.put("information","任务失败：失败位置uid="+num);
            para.put("time",toolMod.time());
            toolDao.taskInformation(para);
        }
    }
}
