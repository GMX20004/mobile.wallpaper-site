package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "information")
public class YmlConfig {
    private String wallpaperDisk;
    private String appid;
    private String secret;

    public String getWallpaperDisk() {
        return wallpaperDisk;
    }

    public void setWallpaperDisk(String wallpaperDisk) {
        this.wallpaperDisk = wallpaperDisk;
    }

    public Map<String,String> getWx(){
        Map<String,String> map = new HashMap<>();
        map.put("appid",appid);
        map.put("secret",secret);
        return map;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
