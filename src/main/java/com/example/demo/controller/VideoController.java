package com.example.demo.controller;

import com.example.demo.dao.VideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频接口
 */
@RestController
@CrossOrigin(value = {"http://101.43.88.137:9080","http://39.187.88.250:9080","http://192.168.1.19:9080"},allowCredentials = "true")
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoDao videoDao;

    /**
     *视频列表
     */
    @GetMapping("viewList")
    public List viewList(@RequestParam int page){
        int start = 0;
        for (int i=1;i<page;i++) start+=10;
        List arr = videoDao.listCode(start);
        if (page*10>videoDao.numberCode())
            arr.add(true);
        else
            arr.add(false);
        return arr;
    }
}
