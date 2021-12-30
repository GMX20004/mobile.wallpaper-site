package com.example.demo.controller;


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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 壁纸相关接口
 */
@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/Wallpaper")
public class WallpaperController {

    @Autowired
    private WallpaperSortingDao wallpaperSortingDao;
    @Autowired
    private UserDao userDao;
    ToolMod toolMod = new ToolMod();
    /**
     * 搜索
     */
    @GetMapping("search")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "value", value = "搜索内容", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    })
    public List search(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.valueOf(params.get("page").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=10;
        params.put("value","%"+params.get("value")+"%");
        params.put("start",start);
        List arr = wallpaperSortingDao.searchCode(params);
        arr.add(wallpaperSortingDao.searchCountCode(params));
        return arr;
    }

    /**
     *标签查询
     */
    @GetMapping("label")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "value", value = "标签内容", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    })
    public List  label(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.valueOf(params.get("page").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=10;
        params.put("value","%"+params.get("value")+"%");
        params.put("start",start);
        List arr = wallpaperSortingDao.labelCode(params);
        arr.add(wallpaperSortingDao.labelCountCode(params));
        return arr;
    }
    /**
     *每日默认显示
     */
    @GetMapping("daily")
    @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    public List daily(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.valueOf(params.get("page").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=10;
        params.put("start",start);
        List arr = wallpaperSortingDao.dailyCode(params);
        arr.add(wallpaperSortingDao.countCode());
        return arr;
    }
    /**
     *每日热门显示
     */
    @GetMapping("hot")
    @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    public List hot(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.valueOf(params.get("page").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=10;
        params.put("start",start);
        List arr = wallpaperSortingDao.hotCode(params);
        arr.add(wallpaperSortingDao.countCode());
        return arr;
    }

    /**
     * 最新上架显示
     */
    @GetMapping("latest")
    @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    public List latest(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.valueOf(params.get("page").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=10;
        params.put("start",start);
        List arr = wallpaperSortingDao.latestCode(params);
        arr.add(wallpaperSortingDao.countCode());
        return arr;
    }

    /**
     *点赞最多显示
     */
    @GetMapping("praise")
    @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    public List praise(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.valueOf(params.get("page").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=10;
        params.put("start",start);
        List arr = wallpaperSortingDao.praiseCode(params);
        arr.add(wallpaperSortingDao.countCode());
        return arr;
    }
    /**
     * 收藏最多显示
     */
    @GetMapping("collection")
    @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    public List collection(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.valueOf(params.get("page").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=10;
        params.put("start",start);
        List arr = wallpaperSortingDao.collectionCode(params);
        arr.add(wallpaperSortingDao.countCode());
        return arr;
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
        int num = Integer.valueOf(params.get("type").toString());
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
        List<WallpaperDetailsDTO> arr = wallpaperSortingDao.detailsWallpaperCode(params);
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
            int num = Integer.valueOf(params.get("type").toString());
            if (num==1){
                List<WallpaperDetailsDTO> arr = wallpaperSortingDao.detailsWallpaperCode(params);
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
