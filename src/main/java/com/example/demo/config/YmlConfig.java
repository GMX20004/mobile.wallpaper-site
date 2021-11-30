package com.example.demo.config;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "information")
public class YmlConfig {
    private String wallpaperDisk;


    public String getWallpaperDisk() {
        return wallpaperDisk;
    }

    public void setWallpaperDisk(String wallpaperDisk) {
        this.wallpaperDisk = wallpaperDisk;
    }

    @Override
    public String toString() {
        return "YmlConfig{" +
                "wallpaperDisk='" + wallpaperDisk + '\'' +
                '}';
    }
}
