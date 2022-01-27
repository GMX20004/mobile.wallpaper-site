package com.example.demo.task;


import cn.hutool.http.HttpUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class timingTask {
    @Scheduled(cron = "0 0 1 * * ?")
    public void executeFileDow(){
        HttpUtil.get("http://localhost:8080/admin/576f7da7bc264e63a923bfa16d0f133d");
        System.out.println("定时任务执行");
    }
}
