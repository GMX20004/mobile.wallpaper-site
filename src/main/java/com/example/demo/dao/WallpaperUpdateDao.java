package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * 壁纸每日显示更新
 */
@Mapper
public interface WallpaperUpdateDao {

    //壁纸首页显示每日变动
    @Update("UPDATE `wallpaper` SET the_default_daily = #{random} WHERE id = #{id}")
    int theDefaultCode(Map<String,Object> param);
    //壁纸首页显示每日变动
    @Update("UPDATE `wallpaper` SET hot = #{random} WHERE id = #{id}")
    int dailyHotCode(Map<String,Object> param);

}
