package com.example.demo.controller;


import com.example.demo.config.YmlConfig;
import com.example.demo.dao.UserDao;
import com.example.demo.dao.WallpaperSortingDao;
import com.example.demo.dto.WallpaperDTO;
import com.example.demo.dto.WallpaperDetailsDTO;
import com.example.demo.mod.ToolMod;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 壁纸相关接口
 */
@RestController
@RequestMapping("/Wallpaper")
public class WallpaperController {

    @Autowired
    private WallpaperSortingDao wallpaperSortingDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private YmlConfig ymlConfig;
    /**
     * 搜索
     */
    @GetMapping("search")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "value", value = "搜索内容", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "limit", value = "限制", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    })
    public Map<String,Object> search(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=10;
        params.put("value","%"+params.get("value")+"%");
        params.put("start",start);
        params.put("limit",limit);
        Map<String,Object> map = new HashMap<>();
        map.put("data",wallpaperSortingDao.searchCode(params));
        map.put("total",wallpaperSortingDao.searchCountCode(params));
        return map;
    }

    /**
     *标签查询
     */
    @GetMapping("label")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "value", value = "标签内容", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "limit", value = "限制", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    })
    public Map<String,Object>  label(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=limit;
        params.put("value","%"+params.get("value")+"%");
        params.put("start",start);
        params.put("limit",limit);
        Map<String,Object> map = new HashMap<>();
        map.put("data",wallpaperSortingDao.labelCode(params));
        map.put("total",wallpaperSortingDao.labelCountCode(params));
        return map;
    }
    /**
     *每日默认显示
     */
    @GetMapping("daily")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "限制", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    })
    public Map<String,Object> daily(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=limit;
        params.put("start",start);
        params.put("limit",limit);
        Map<String,Object> map = new HashMap<>();
        map.put("data",wallpaperSortingDao.dailyCode(params));
        map.put("total",wallpaperSortingDao.countCode());
        return map;
    }
    /**
     *每日热门显示
     */
    @GetMapping("hot")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "限制", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    })
    public Map<String,Object> hot(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=limit;
        params.put("start",start);
        params.put("limit",limit);
        Map<String,Object> map = new HashMap<>();
        map.put("data",wallpaperSortingDao.hotCode(params));
        map.put("total",wallpaperSortingDao.countCode());
        return map;
    }

    /**
     * 最新上架显示
     */
    @GetMapping("latest")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "限制", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    })
    public Map<String,Object> latest(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=limit;
        params.put("start",start);
        params.put("limit",limit);
        Map<String,Object> map = new HashMap<>();
        map.put("data",wallpaperSortingDao.latestCode(params));
        map.put("total",wallpaperSortingDao.countCode());
        return map;
    }

    /**
     *点赞最多显示
     */
    @GetMapping("praise")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "限制", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    })
    public Map<String,Object> praise(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=limit;
        params.put("start",start);
        params.put("limit",limit);
        Map<String,Object> map = new HashMap<>();
        map.put("data",wallpaperSortingDao.praiseCode(params));
        map.put("total",wallpaperSortingDao.countCode());
        return map;
    }
    /**
     * 收藏最多显示
     */
    @GetMapping("collection")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "限制", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    })
    public Map<String,Object> collection(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=limit;
        params.put("start",start);
        params.put("limit",limit);
        Map<String,Object> map = new HashMap<>();
        map.put("data",wallpaperSortingDao.collectionCode(params));
        map.put("total",wallpaperSortingDao.countCode());
        return map;
    }
    /**
     * 用户收藏投稿点赞壁纸查看
     */
    @PostMapping("collectionContribute")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query",required = true, dataType="int"),
        @ApiImplicitParam(name = "type", value = "0收藏1点赞2投稿", paramType = "query",required = true, dataType="int")
    })
    public List<WallpaperDTO> collectionContribute(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.parseInt(params.get("type").toString());
        if (num==2) return wallpaperSortingDao.uploadCode(params);
        else return wallpaperSortingDao.collectionGiveALikeCode(params);
    }
    /**
     * 查看壁纸详情
     */
    @PostMapping("wallpaper")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "壁纸id", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query",dataType="int")
    })
    public List<WallpaperDetailsDTO> wallpaper(@ApiIgnore @RequestParam Map<String, Object> params){
        List<WallpaperDetailsDTO> arr = wallpaperSortingDao.detailsWallpaperCode(Integer.parseInt(params.get("id").toString()));
        if(params.get("userId")!=null || params.get("userId")!=""){
            params.put("type",0);
            int num = wallpaperSortingDao.isWallpaperTypeCode(params);
            arr.get(0).setIsCollection(num);
            params.put("type",1);
            num = wallpaperSortingDao.isWallpaperTypeCode(params);
            arr.get(0).setIsPraise(num);
        }else {
            System.out.println(1);
            arr.get(0).setIsCollection(0);
            arr.get(0).setIsPraise(0);
        }
        return arr;
    }
    /**
     * 壁纸点赞和收藏
     */
    @PostMapping("obtainCancel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "壁纸id", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query",required = true,dataType="int"),
            @ApiImplicitParam(name = "type", value = "0收藏1点赞", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "is", value = "0取消1获取", paramType = "query",required = true, dataType="int")
    })
    public boolean obtainCancel(@ApiIgnore @RequestParam Map<String, Object> params){
        try {
            wallpaperSortingDao.zoAddDeleteCGCode(params);
            if (params.get("userId")!=null && params.get("userId")!=""){
                wallpaperSortingDao.obtainCancelCGCode(params);
            }
            int num = Integer.parseInt(params.get("type").toString());
            if (num==1){
                List<WallpaperDetailsDTO> arr = wallpaperSortingDao.detailsWallpaperCode(Integer.parseInt(params.get("id").toString()));
                params.put("id",arr.get(0).getUserId());
                userDao.userAddDeleteCGCode(params);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
