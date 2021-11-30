package com.example.test;

import com.example.demo.dao.WallpaperUpdateDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {
    @Autowired
    private WallpaperUpdateDao wallpaperUpdateDao;
    @Test
    void contextLoads() {

    }

}
