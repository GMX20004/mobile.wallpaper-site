package com.example.demo.task;


import cn.hutool.http.HttpUtil;
import com.example.demo.dao.ToolDao;
import com.example.demo.mod.ToolMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class timingTask {
    @Autowired
    private ToolDao toolDao;
    ToolMod toolMod = new ToolMod();
    @Scheduled(cron = "0 0 0 * * ?")
    public void executeFileDow(){
        Map<String,Object> params = new HashMap<>();
        try{
            HttpUtil.get("http://localhost:8080/admin/576f7da7bc264e63a923bfa16d0f133d?type=1");
            params.put("name","每日首页壁纸显示改动");
            params.put("information","自动执行更新");
            params.put("time",toolMod.time());
            toolDao.taskInformation(params);
        }catch (Exception e){
            params.put("name","每日首页壁纸显示改动");
            if (e.getLocalizedMessage().length()>990){
                params.put("information","更新失败:"+e.getLocalizedMessage().substring(0,990));
            }else{
                params.put("information","更新失败:"+e.getLocalizedMessage());
            }
            params.put("time",toolMod.time());
            toolDao.taskInformation(params);
        }

    }
}
