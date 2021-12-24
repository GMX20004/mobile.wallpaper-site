package com.example.demo.mod;

import com.example.demo.dao.WallpaperSortingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 壁纸每日更新多线程
 */
@Component
public class DailyChange extends Thread{
    @Autowired
    private WallpaperSortingDao wallpaperSortingDao;
    private static WallpaperSortingDao wsd;
    private String type;

    @PostConstruct
    public void init(){
        wsd=this.wallpaperSortingDao;
    }
    public void setType(String num){
        type=num;
    }
    @Override
    public void run() {
        try{
            ToolMod toolMod = new ToolMod();
            Map<String,Object> params = new HashMap<>();
            params.put("type",type);
            String sql="CASE";
            int num = wsd.countCode();
            LinkedList a = new LinkedList();
            for (int i = 1; i <= num; i++) a.add(i);
            for (int i = 1; i <= num; i++) {
                int rand = toolMod.randomDigital(a.size());
                sql += " WHEN id = "+i+" THEN "+a.get(rand-1)+"";
                a.remove(rand - 1);
            }
            params.put("sql",sql);
            wsd.theDefaultCode(params);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
