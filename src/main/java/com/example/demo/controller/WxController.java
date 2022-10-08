package com.example.demo.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.YmlConfig;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserDTO;
import com.example.demo.mod.ToolMod;
import com.example.demo.result.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/WX")
public class WxController {
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private UserDao userDao;

    ToolMod toolMod = new ToolMod();
    /**
     * 获取用户唯一标识获取
     * @param code
     * @return
     */
    @GetMapping("openid")
    public ResultData<JSONObject> openid(@RequestParam String code){
        try {
            Map<String,String> map = ymlConfig.getWx();
            String url = "https://api.weixin.qq.com/sns/jscode2session";
            String value = "appid="+map.get("appid")+"&secret="+map.get("secret")+"&js_code="+code+"&grant_type=authorization_code";
            JSONObject jsonObj = JSON.parseObject(HttpUtil.post(url,value));
            if (jsonObj.get("openid")==null){
                return ResultData.fail(-1,"code错误");
            }else{
                return ResultData.success(jsonObj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultData.fail(-1,"失败");
    }

    @GetMapping("user")
    public ResultData<UserDTO> user(@RequestParam String openid){
        try {
            if (userDao.getIsExistCode("user_id = '"+openid+"'") == 0){
                Map<String,Object> map = new HashMap<>();
                map.put("email","null");
                map.put("password","null");
                map.put("uuid",openid);
                map.put("time",toolMod.time());
                map.put("width",0);
                map.put("height",0);
                userDao.getRegisteredCode(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.fail(-1,"失败");
        }
        return ResultData.success(userDao.getWxUser(openid).get(0));
    }
}
