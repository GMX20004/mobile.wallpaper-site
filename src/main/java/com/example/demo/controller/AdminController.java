package com.example.demo.controller;

import com.example.demo.config.YmlConfig;
import com.example.demo.dao.ToolDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dao.WallpaperSortingDao;
import com.example.demo.dto.*;
import com.example.demo.mod.DailyChange;
import com.example.demo.mod.ToolMod;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.util.*;

/**
 * 管理员接口
 * Linux文件路径使用/,windows文件路径为\\
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private WallpaperSortingDao wallpaperSortingDao;
    @Autowired
    private ToolDao toolDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private YmlConfig ymlConfig;

    ToolMod toolMod = new ToolMod();
    //windows
    //String slash = "\\";
    //Linux
    String slash = "/";
    /**
     * 每日首页壁纸显示改动
     */
    @GetMapping("576f7da7bc264e63a923bfa16d0f133d")
    public Boolean dailyChange(@ApiIgnore @RequestParam Map<String, Object> params){
        try {
            DailyChange dailyChange1 = new DailyChange();
            dailyChange1.setType("the_default_daily");
            dailyChange1.start();
            DailyChange dailyChange2 = new DailyChange();
            dailyChange2.setType("hot");
            dailyChange2.start();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 所有用户查看
     */
    @GetMapping("c896d9988afd44939906b45e8703df3a")
    public List<UserDTO> userView(){
        return userDao.userViewCode();
    }
    /**
     * 删除用户
     */
    @PostMapping("98fd879c7a7147119c8814c57f14898a")
    @ApiImplicitParam(name = "id", value = "用户编号ID", paramType = "query",required = true, dataType="int")
    public Boolean deleteUser(@ApiIgnore @RequestParam Map<String, Object> params){
        try {
            userDao.deleteUserCode(params);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 更改用户状态
     */
    @PostMapping("cec1427431ef4ca99503cd35c8c40bf9")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号ID", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "state", value = "状态", paramType = "query",required = true, dataType="int")
    })
    public Boolean stateUser(@ApiIgnore @RequestParam Map<String, Object> params){
        try {
            userDao.stateUserCode(params);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 审查壁纸全部
     */
    @GetMapping("0529588ecb8d4246bc0dc5302643b62d")
    public List<WallpaperDTO> reviewWallpaper(){
        return wallpaperSortingDao.reviewWallpaperCode();
    }
    /**
     * 审查壁纸详情查看
     */
    @PostMapping("efa0fc9f51224275943c116038fdcd6b")
    @ApiImplicitParam(name = "id", value = "壁纸id", paramType = "query",required = true, dataType="int")
    public List<WallpaperDetailsDTO> detailsWallpaperLin(@ApiIgnore @RequestParam Map<String, Object> params){
        return wallpaperSortingDao.detailsWallpaperLinCode(params);
    }
    /**
     *审核通过
     */
    @PostMapping("1e715da537b946cba23fb03828537529")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "壁纸id", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "storageLocation", value = "存储文件夹名", paramType = "query",required = true, dataType="String")
    })

    public boolean reviewThrough(@ApiIgnore @RequestParam Map<String, Object> params){
        try {
            int num = wallpaperSortingDao.countCode();
            Object id = params.get("id");
            List<WallpaperDetailsDTO> arr = wallpaperSortingDao.detailsWallpaperLinCode(params);
            params.put("id",num+1);
            params.put("theTitle",arr.get(0).getTheTitle());
            params.put("userId",arr.get(0).getUserId());
            params.put("theLabel",arr.get(0).getTheLabel());
            params.put("type",arr.get(0).getType());
            params.put("size",arr.get(0).getSize());
            String target = ymlConfig.getWallpaperDisk()+"cs"+slash+id+"."+arr.get(0).getType();
            String destination = ymlConfig.getWallpaperDisk()+params.get("storageLocation")+slash+params.get("id")+"."+arr.get(0).getType();
            File targetFile = new File(ymlConfig.getWallpaperDisk()+params.get("storageLocation"));
            if (!targetFile.exists()) {
                // 判断文件夹是否未空，空则创建
                targetFile.mkdirs();
            }
            toolMod.imgTransfer(target,destination);
            wallpaperSortingDao.reviewThroughCode(params);
            params.put("id",id);
            wallpaperSortingDao.deleteAuditCode(params);
            toolMod.deleteFile(target);
            userDao.userContributeCode(arr.get(0).getUserId());
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 审核不通过
     */
    @PostMapping("ec453f2adc63493db651d8a7e7e98191")
    @ApiImplicitParam(name = "id", value = "壁纸编号", paramType = "query",required = true, dataType="int")
    public boolean reviewNotThrough(@ApiIgnore @RequestParam Map<String, Object> params){
        try {
            List<WallpaperDetailsDTO> arr = wallpaperSortingDao.detailsWallpaperLinCode(params);
            System.out.println(arr);
            params.put("message","管理员:亲爱的用户您好!您于"+arr.get(0).getCreationTime()+"上传名为<"+arr.get(0).getTheTitle()+">的壁纸未通过审核。对您带来的不便我们深感抱歉,欢迎您再次投递分享,谢谢!");
            params.put("accept",arr.get(0).getUserId());
            params.put("send",0);
            params.put("level",1);
            toolDao.sendAMessageCode(params);
            wallpaperSortingDao.deleteAuditCode(params);
            String target = ymlConfig.getWallpaperDisk()+"cs"+slash+params.get("id")+"."+arr.get(0).getType();
            System.out.println(target);
            toolMod.deleteFile(target);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 意见反馈查看
     */
    @GetMapping("f91bcfccb27d4f02ac249733e495d518")
    public List<FeedbackDTO> feedback(){
        return toolDao.feedbackCode();
    }
    /**
     * 删除反馈
     */
    @PostMapping("90029510feae426aaa31c8560d4ee6a2")
    @ApiImplicitParam(name = "id", value = "id", paramType = "query",required = true, dataType="int")
    public boolean deleteFeedback(@ApiIgnore @RequestParam Map<String, Object> params){
        int i = toolDao.deleteFeedbackCode(params);
        if (i==1)return true;
        else return false;
    }

    /**
     * 修改审核壁纸
     */
    @PostMapping("ccef83e1d2ff455fae16680c906f2239")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "theTitle", value = "标题", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "theLabel", value = "标签", paramType = "query",required = true, dataType="String")
    })
    public boolean modifyAudit(@ApiIgnore @RequestParam Map<String, Object> params){
        int i = wallpaperSortingDao.modifyAuditCode(params);
        if (i==1)return true;
        else return false;
    }
    /**
     * 获取管理员消息
     */
    @PostMapping("ab7da92a50e94363a19fb6740b2de54e")
    public List<MessagesDTO> receiveMessages(@RequestParam int id){
        List<MessagesDTO> arr = toolDao.receiveAdminMessagesCode(id);
        for (int i=0;i<arr.size();i++){
            toolDao.deleteMessagesCode(arr.get(i).getId());
        }
        return arr;
    }

    /**
     *  管理员登录
     */
    @PostMapping("e4c984df9f364376992066fd393d89fe")
    public List login(@RequestParam String password){
        Map<String,Object> params = new HashMap<>();
        List pan = new ArrayList();
        try {
            params.put("email",0);
            params.put("password",password);
            List<UserDTO> arr = userDao.getLogInToCode(params);
            if (arr!=null&&arr.size()!=0){
                pan.add(true);
                params.put("id",0);
                params.put("uuid",toolMod.uuid());
                userDao.userUpdateUuidCode(params);
                arr = userDao.getLogInToCode(params);
                pan.add(arr.get(0).getUserId());
            }else {
                pan.add(false);
            }
            userDao.userUuidCode(params);
        }catch (Exception e){
            e.printStackTrace();
            pan.add(false);
        }
        return pan;
    }
    /**
     * 判断用户唯一编码
     */
    @PostMapping("443139dfab264464a40e7f7425588469")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "uuid", value = "唯一编码", paramType = "query",required = true, dataType="String")
    })
    public boolean uuid(@ApiIgnore @RequestParam Map<String, Object> params){
        if (userDao.userUuidCode(params)==1)
            return true;
        else
            return false;
    }
    /**
     * 查看壁纸文件夹
     */
    @GetMapping("586c0e7bda874d5fa1749c56963077dc")
    public List<wallpaperFolderDTO> wallpaperFolder(){
        return toolDao.wallpaperFolderCode();
    }
    /**
     * 新增壁纸文件夹
     */
    @PostMapping("a88b00a562624a51badbbb509d0e3b92")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "folder", value = "文件夹名", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "note", value = "备注", paramType = "query",required = true, dataType="String")
    })
    public int wallpaperFolderInsert(@ApiIgnore @RequestParam Map<String, Object> params){
        return toolDao.wallpaperFolderInsertCode(params);
    }

    /**
     *指定壁纸显示最上面
     */
    @PostMapping("32f9f4ffd0a04d0f9bf5c7a4a9420b5e")
    public boolean wallpaperTop(@RequestParam String value){
        try {
            List<WallpaperDTO> arr = wallpaperSortingDao.wallpaperTop("%"+value+"%");
            Map<String,Object> params = new HashMap<>();
            String sql = "CASE";
            for (int i=0;i<arr.size();i++){
                sql += " WHEN id = "+arr.get(i).getId()+" THEN "+i*-1+"";
            }
            params.put("sql",sql);
            params.put("type","the_default_daily");
            wallpaperSortingDao.theDefaultCode(params);
            params.put("type","hot");
            wallpaperSortingDao.theDefaultCode(params);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
