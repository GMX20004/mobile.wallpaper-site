package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dto.WallpaperDTO;
import com.example.demo.dto.WallpaperDetailsDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 *壁纸
 */
@Mapper
public interface WallpaperSortingDao extends BaseMapper<WallpaperDTO> {
    //计数总数
    @Select("SELECT COUNT(*) FROM wallpaper")
    int countCode();
    //审核壁纸计数总数
    @Select("SELECT COUNT(*) FROM wallpaper_lins")
    int auditCountCode();
    //条件计数总数
    @Select("SELECT COUNT(*) FROM wallpaper WHERE user_id = #{userId}")
    int countUserCode(Map<String,Object> param);
    //每日默认显示
    @Select("SELECT id,`type`,storage_location FROM wallpaper ORDER BY the_default_daily LIMIT #{start},10")
    List<WallpaperDTO>dailyCode(Map<String,Object> param);
    //热门显示
    @Select("SELECT id,`type`,storage_location FROM wallpaper ORDER BY hot LIMIT #{start},10")
    List<WallpaperDTO>hotCode(Map<String,Object> param);
    //最新上架
    @Select("SELECT id,`type`,storage_location FROM wallpaper ORDER BY id DESC LIMIT #{start},10")
    List<WallpaperDTO>latestCode(Map<String,Object> param);
    //点赞最多显示
    @Select("SELECT id,`type`,storage_location FROM wallpaper ORDER BY praise DESC LIMIT #{start},10")
    List<WallpaperDTO>praiseCode(Map<String,Object> param);
    //收藏最多显示
    @Select("SELECT id,`type`,storage_location FROM wallpaper ORDER BY collection DESC LIMIT #{start},10")
    List<WallpaperDTO>collectionCode(Map<String,Object> param);
    //搜索查询
    @Select("SELECT id,`type`,storage_location FROM `wallpaper` WHERE the_title LIKE #{value} OR the_label LIKE #{value} LIMIT #{start},10")
    List<WallpaperDTO>searchCode(Map<String,Object> param);
    //搜索查询结果数
    @Select("SELECT COUNT(*) FROM `wallpaper` WHERE the_title LIKE concat(#{value},'%') OR the_label LIKE CONCAT(#{value},'%')")
    int searchCountCode(Map<String,Object> param);
    //标签查询
    @Select("SELECT id,`type`,storage_location FROM `wallpaper` WHERE the_label LIKE #{value} LIMIT #{start},10")
    List<WallpaperDTO>labelCode(Map<String,Object> param);
    //标签查询结果数
    @Select("SELECT COUNT(*) FROM `wallpaper` WHERE the_label LIKE CONCAT(#{value},'%')")
    int labelCountCode(Map<String,Object> param);
    //查询壁纸是否点赞收藏
    @Select("SELECT count(*) FROM wallpaper_type WHERE id = #{id} AND user_id = #{userId} AND type = #{type}")
    int isWallpaperTypeCode(Map<String,Object> param);
    //获取用户点赞收藏壁纸
    @Select("SELECT t1.id,t2.type,t2.storage_location FROM wallpaper_type t1 LEFT JOIN wallpaper t2 ON t1.id=t2.id WHERE t1.user_id=#{userId} and t1.type=#{type}")
    List<WallpaperDTO> collectionGiveALikeCode(Map<String,Object> param);
    //获取用户投稿壁纸
    @Select("SELECT id,`type`,storage_location FROM wallpaper WHERE user_id=#{userId}")
    List<WallpaperDTO> uploadCode(Map<String,Object> param);
    //点击或取消点赞收藏
    void obtainCancelCGCode(Map<String,Object> param);
    //点赞收藏总数修改
    void zoAddDeleteCGCode(Map<String,Object> param);
    //审核壁纸--管理员权限
    @Select("SELECT * FROM wallpaper_lins")
    List<WallpaperDTO> reviewWallpaperCode();
    //壁纸审核通过--管理员权限
    @Insert("INSERT INTO wallpaper(id,the_title,user_id,the_label,type,storage_location,size)VALUES(#{id},#{theTitle},#{userId},#{theLabel},#{type},#{storageLocation},#{size})")
    int reviewThroughCode(Map<String,Object> param);
    //壁纸详情查看
    @Select("SELECT t1.id,t1.praise,t1.collection,t1.the_title,t1.user_id,t1.the_label,t1.type,t1.storage_location,t1.creation_time,t2.`name`,t2.head_portrait FROM wallpaper t1 LEFT JOIN `user` t2 ON t1.user_id=t2.id WHERE t1.id=#{id}")
    List<WallpaperDetailsDTO> detailsWallpaperCode(Map<String,Object> param);
    //壁纸审核详情查看--管理员权限
    @Select("SELECT t1.id,t1.the_title,t1.user_id,t1.the_label,t1.type,t1.creation_time,t1.size,t2.`name`,t2.head_portrait FROM wallpaper_lins t1 LEFT JOIN `user` t2 ON t1.user_id=t2.id WHERE t1.id=#{id}")
    List<WallpaperDetailsDTO> detailsWallpaperLinCode(Map<String,Object> param);
    //
    @Select("SELECT id FROM wallpaper_lins WHERE coding=#{uuid}")
    int detailsWallpaperLinUuidCode(String uuid);
    //删除审核壁纸--管理员权限
    @Delete("DELETE FROM `wallpaper_lins` WHERE id=#{id}")
    int deleteAuditCode(Map<String,Object> param);
    //壁纸上传
    @Insert("INSERT INTO wallpaper_lins(the_title,user_id,the_label,type,coding,size)VALUES(#{theTitle},#{userId},#{theLabel},#{type},#{coding},#{size})")
    int uploadWallpaperCode(Map<String,Object> param);
    //修改审核壁纸
    @Update("UPDATE `wallpaper_lins` SET the_title=#{theTitle},the_label=#{theLabel} WHERE id = #{id}")
    int modifyAuditCode(Map<String,Object> param);
}
