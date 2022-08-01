package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.body.ModifyAuditBody;
import com.example.demo.body.PermissionsBody;
import com.example.demo.config.YmlConfig;
import com.example.demo.dao.ToolDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dao.WallpaperSortingDao;
import com.example.demo.dto.*;
import com.example.demo.mod.DailyChange;
import com.example.demo.mod.ToolMod;
import com.example.demo.utils.excel.ExcelUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * 管理员接口
 * Linux文件路径使用/,windows文件路径为\\
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "admin", produces = "application/json;charset=UTF-8")
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
    public Boolean dailyChange(@RequestParam int type){
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
        if (type==0){
            Map<String,Object> params = new HashMap<>();
            params.put("name","每日首页壁纸显示改动");
            params.put("information","手动执行更新");
            params.put("time",toolMod.time());
            toolDao.taskInformation(params);
        }
        return true;
    }
    /**
     * 查看所有普通用户
     */
    @GetMapping("c896d9988afd44939906b45e8703df3a")
    @ApiImplicitParams({
            @ApiImplicitParam (name = "identity", value = "用户身份", paramType = "query",required = false, dataType="int"),
            @ApiImplicitParam(name = "uuid", value = "管理员授权码", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "limit", value = "限制", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int")
    })
    public Map<String,Object> userView(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = Integer.valueOf(params.get("page").toString());
        int limit = Integer.valueOf(params.get("limit").toString());
        int start = 0;
        for (int i=1;i<num;i++) start+=limit;
        params.put("start",start);
        params.put("limit",limit);
        if(userDao.userUuidCode(params)==1){
            Map<String,Object> map = new HashMap<>();
            if (params.get("identity") != null){
                params.put("sql","identity="+params.get("identity"));
            }else{
                params.put("sql","1=1");
            }
            map.put("data",userDao.userViewCode(params));
            map.put("total",userDao.userNumCode(params));
            return map;
        }
        else
            return null;
    }
    /**
     * 删除用户
     */
    @PostMapping("98fd879c7a7147119c8814c57f14898a")
    @ApiImplicitParams({
        @ApiImplicitParam (name = "id", value = "用户编号ID", paramType = "query",required = true, dataType="int"),
        @ApiImplicitParam(name = "uuid", value = "管理员授权码", paramType = "query",required = true, dataType="String")
    })
    public Boolean deleteUser(@ApiIgnore @RequestParam Map<String, Object> params){
        try {
            if(userDao.userUuidCode(params)==1) {
                userDao.deleteUserCode(params);
                return true;
            }
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
            @ApiImplicitParam(name = "state", value = "状态", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "uuid", value = "管理员授权码", paramType = "query",required = true, dataType="String")
    })
    public Boolean stateUser(@ApiIgnore @RequestParam Map<String, Object> params){
        try {
            if(userDao.userUuidCode(params)==1) {
                userDao.stateUserCode(params);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 审查壁纸全部
     */
    @GetMapping("0529588ecb8d4246bc0dc5302643b62d")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "限制", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "uuid", value = "管理员授权码", paramType = "query",required = true, dataType="String")
    })
    public Map<String,Object> reviewWallpaper(@ApiIgnore @RequestParam Map<String, Object> params){
        if(userDao.userUuidCode(params)==1 || "000000".equals(params.get("uuid").toString())){
            int num = Integer.valueOf(params.get("page").toString());
            int limit = Integer.valueOf(params.get("limit").toString());
            int start = 0;
            for (int i=1;i<num;i++) start+=limit;
            params.put("start",start);
            params.put("limit",limit);
            Map<String,Object> map = new HashMap<>();
            map.put("data",wallpaperSortingDao.reviewWallpaperCode(params));
            map.put("total",wallpaperSortingDao.countCode());
            return map;
        }
        else
            return null;
    }
    /**
     * 审查壁纸详情查看
     */
    @PostMapping("efa0fc9f51224275943c116038fdcd6b")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "壁纸id", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "uuid", value = "管理员授权码", paramType = "query",required = true, dataType="String")
    })
    public List<WallpaperDetailsDTO> detailsWallpaperLin(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("email",0);
        if(userDao.userUuidCode(params)==1)
            return wallpaperSortingDao.detailsWallpaperLinCode(params);
        else
            return null;
    }
    /**
     *审核通过
     */
    @PostMapping("1e715da537b946cba23fb03828537529")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "壁纸id", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "storageLocation", value = "存储文件夹名", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "uuid", value = "管理员授权码", paramType = "query",required = true, dataType="String")
    })

    public boolean reviewThrough(@ApiIgnore @RequestParam Map<String, Object> params){
        try {
            if(userDao.userUuidCode(params)==1){
                List<WallpaperDetailsDTO> arr = wallpaperSortingDao.detailsWallpaperLinCode(params);
                String target = ymlConfig.getWallpaperDisk()+"cs"+slash+params.get("id")+"."+arr.get(0).getType();
                String destination = ymlConfig.getWallpaperDisk()+params.get("storageLocation")+slash+params.get("id")+"."+arr.get(0).getType();
                File targetFile = new File(ymlConfig.getWallpaperDisk()+params.get("storageLocation"));
                if (!targetFile.exists()) {
                    // 判断文件夹是否未空，空则创建
                    targetFile.mkdirs();
                }
                toolMod.imgTransfer(target,destination);
                wallpaperSortingDao.reviewThroughCode(params);
                params.put("id",params.get("id"));
                wallpaperSortingDao.deleteAuditCode(params);
                toolMod.deleteFile(target);
                userDao.userContributeCode(arr.get(0).getUserId());
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 审核不通过
     */
    @PostMapping("ec453f2adc63493db651d8a7e7e98191")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "壁纸编号", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "uuid", value = "管理员授权码", paramType = "query",required = true, dataType="String")
    })
    public boolean reviewNotThrough(@ApiIgnore @RequestParam Map<String, Object> params){
        try {
            if(userDao.userUuidCode(params)==1){
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
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 意见反馈查看
     */
    @GetMapping("f91bcfccb27d4f02ac249733e495d518")
    @ApiImplicitParam(name = "uuid", value = "管理员授权码", paramType = "query",required = true, dataType="String")
    public List<FeedbackDTO> feedback(@ApiIgnore @RequestParam Map<String, Object> params){
        if(userDao.userUuidCode(params)==1)
            return toolDao.feedbackCode();
        else
            return null;
    }
    /**
     * 删除反馈
     */
    @PostMapping("90029510feae426aaa31c8560d4ee6a2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "uuid", value = "管理员授权码", paramType = "query",required = true, dataType="String")
    })
    public boolean deleteFeedback(@ApiIgnore @RequestParam Map<String, Object> params){
        if(userDao.userUuidCode(params)==1){
            int i = toolDao.deleteFeedbackCode(params);
            if (i==1)return true;
            else return false;
        }else
            return false;
    }

    /**
     * 修改壁纸信息
     */
    @PostMapping("ccef83e1d2ff455fae16680c906f2239")
    public boolean modifyAudit(@RequestBody @NotNull ModifyAuditBody modifyAuditBody, HttpServletRequest httpRequest){
        Map<String,Object> params = new HashMap<>();
        params.put("id", modifyAuditBody.getId());
        params.put("theTitle", modifyAuditBody.getTheTitle());
        params.put("theLabel", modifyAuditBody.getTheLabel());
        params.put("storageLocation", modifyAuditBody.getStorageLocation());
        params.put("state", modifyAuditBody.getState());
        params.put("uuid", modifyAuditBody.getUuid());
        if( userDao.userUuidCode(params)==1 ){
            List<WallpaperDetailsDTO> arr = wallpaperSortingDao.detailsWallpaperCode(params);
            if (modifyAuditBody.getTheTitle()==null || "".equals(modifyAuditBody.getTheTitle())){
                params.put("theTitle",arr.get(0).getTheTitle());
            }
            if (modifyAuditBody.getTheLabel()==null || "".equals(modifyAuditBody.getTheLabel())){
                params.put("theLabel",arr.get(0).getTheLabel());
            }
            if (!arr.get(0).getStorageLocation().equals(modifyAuditBody.getStorageLocation())){
                String target = ymlConfig.getWallpaperDisk()+arr.get(0).getStorageLocation()+slash+modifyAuditBody.getId()+"."+arr.get(0).getType();
                String destination = ymlConfig.getWallpaperDisk()+modifyAuditBody.getStorageLocation()+slash+modifyAuditBody.getId()+"."+arr.get(0).getType();
                toolMod.imgTransfer(target,destination);
                toolMod.deleteFile(target);
            }
            String ipAddress = httpRequest.getHeader("X-Real-Ip");
            if (StringUtils.isEmpty(ipAddress)) {
                ipAddress = httpRequest.getRemoteAddr();
            }
            toolDao.operationLogAddCode(userDao.getUserUUIDCode(modifyAuditBody.getUuid()).get(0).getId(),"修改编号为:"+modifyAuditBody.getId()+"的壁纸信息",ipAddress);
            int i = wallpaperSortingDao.modifyAuditCode(params);
            if (i==1)return true;
            else return false;
        }else
            return false;
    }
    /**
     * 获取管理员消息
     */
    @PostMapping("ab7da92a50e94363a19fb6740b2de54e")
    public List<MessagesDTO> receiveMessages(@RequestParam int id){
        toolDao.updateMessageCode(id);
        return toolDao.receiveAdminMessagesCode(id);
    }

    /**
     *  管理员登录
     */
    @PostMapping("e4c984df9f364376992066fd393d89fe")
    public Map<String ,Object> login(@RequestParam String account,@RequestParam String password){
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        try {
            params.put("email",account);
            params.put("password",password);
            List<UserDTO> arr = userDao.getLogInToCode(params);
            if (arr!=null&&arr.size()!=0){
                params.put("id",arr.get(0).getId());
                params.put("uuid",toolMod.uuid());
                userDao.userUpdateUuidCode(params);
                arr = userDao.getLogInToCode(params);
                map.put("exists",true);
                map.put("uuid",arr.get(0).getUserId());
            }else {
                map.put("exists",false);
            }
            userDao.userUuidCode(params);
        }catch (Exception e){
            e.printStackTrace();
            map.put("exists",false);
        }
        return map;
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
    public List<wallpaperFolderDTO> wallpaperFolder(){return toolDao.wallpaperFolderCode();}
    /**
     * 新增壁纸文件夹
     */
    @PostMapping("a88b00a562624a51badbbb509d0e3b92")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "folder", value = "文件夹名", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "note", value = "备注", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "uuid", value = "管理员授权码", paramType = "query",required = true, dataType="String")
    })
    public int wallpaperFolderInsert(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("email",0);
        if(userDao.userUuidCode(params)==1)
            return toolDao.wallpaperFolderInsertCode(params);
        else
            return 0;
    }

    /**
     *指定壁纸显示最上面
     */
    @PostMapping("32f9f4ffd0a04d0f9bf5c7a4a9420b5e")
    public boolean wallpaperTop(@RequestParam String value,@RequestParam String uuid){
        try {
            Map<String,Object>params = new HashMap<>();
            params.put("email",0);
            params.put("uuid",uuid);
            if(userDao.userUuidCode(params)==1){
                List<WallpaperDTO> arr = wallpaperSortingDao.wallpaperTop("%"+value+"%");
                String sql = "CASE";
                String endSql = "WHERE";
                for (int i=0;i<arr.size();i++){
                    sql += " WHEN id = "+arr.get(i).getId()+" THEN "+i*-1+"";
                    endSql += " id = "+arr.get(i).getId();
                    if (i+1<arr.size()){
                        endSql+=" OR";
                    }
                }
                System.out.println(endSql);
                params.put("sql",sql);
                params.put("type","the_default_daily");
                params.put("endSql",endSql);
                wallpaperSortingDao.theDefaultCode(params);
                params.put("type","hot");
                wallpaperSortingDao.theDefaultCode(params);
                return true;
            }else
                return false;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 分页查询日志
     */
    @GetMapping("OperationLog")
    public List<OperationLogDTO> operationLog(@RequestParam int page,@RequestParam int limit){
        int start = 0;
        for (int i=1;i<page;i++) start+=limit;
        Map<String ,Object> map = new HashMap<>();
        map.put("start",start);
        map.put("limit",limit);
        return toolDao.operationLogPageCode(map);
    }

    /**
     * 权限查看
     */
    @GetMapping("PermissionsView")
    public List<PermissionsDTO> permissionsView(@RequestParam String uuid){
        return toolDao.permissionsViewCode(uuid);
    }

    /**
     * 权限修改
     */
    @PostMapping("PermissionsModify")
    public Boolean permissionsModify(@RequestBody PermissionsBody permissionsBody,HttpServletRequest httpRequest){
        try {
            List<PermissionsDTO> arr = toolDao.permissionsViewCode(permissionsBody.getUuid());
            if (arr.get(0).getChangePermissions() == 1){
                Map<String,Object> map = new HashMap<>();
                map.put("id",permissionsBody.getId());
                String sql = "";
                sql+=("modifying_user_information="+permissionsBody.getModifyingUserInformation()+",");
                sql+=("system_announcement="+permissionsBody.getSystemAnnouncement()+",");
                sql+=("important_notice="+permissionsBody.getImportantNotice()+",");
                sql+=("important_system_users="+permissionsBody.getImportantSystemUsers()+",");
                sql+=("important_notice_custom="+permissionsBody.getImportantNoticeCustom()+",");
                sql+=("online_wallpaper="+permissionsBody.getOnlineWallpaper()+",");
                sql+=("not_online_wallpaper="+permissionsBody.getNotOnlineWallpaper()+",");
                sql+=("online_wallpaper_modify="+permissionsBody.getOnlineWallpaperModify()+",");
                sql+=("online_wallpaper_modify_title="+permissionsBody.getOnlineWallpaperModifyTitle()+",");
                sql+=("online_wallpaper_modify_label="+permissionsBody.getOnlineWallpaperModifyLabel()+",");
                sql+=("online_wallpaper_modify_state="+permissionsBody.getOnlineWallpaperModifyState()+",");
                sql+=("online_wallpaper_modify_location="+permissionsBody.getOnlineWallpaperModifyLocation()+",");
                sql+=("not_online_wallpaper_modify="+permissionsBody.getNotOnlineWallpaperModify()+",");
                sql+=("not_online_wallpaper_modify_title="+permissionsBody.getNotOnlineWallpaperModifyTitle()+",");
                sql+=("not_online_wallpaper_modify_label="+permissionsBody.getNotOnlineWallpaperModifyLabel()+",");
                sql+=("not_online_wallpaper_modify_state="+permissionsBody.getNotOnlineWallpaperModifyState()+",");
                sql+=("not_online_wallpaper_modify_location="+permissionsBody.getNotOnlineWallpaperModifyLocation()+",");
                sql+=("wallpaper_upload="+permissionsBody.getWallpaperUpload()+",");
                sql+=("wallpaper_download="+permissionsBody.getWallpaperDownload()+",");
                sql+=("log_export="+permissionsBody.getLogExport()+",");
                sql+=("log_view="+permissionsBody.getLogView());
                map.put("sql",sql);
                toolDao.permissionsModifyCode(map);
                String ipAddress = httpRequest.getHeader("X-Real-Ip");
                if (StringUtils.isEmpty(ipAddress)) {
                    ipAddress = httpRequest.getRemoteAddr();
                }
                toolDao.operationLogAddCode(arr.get(0).getId(),"修改权限",ipAddress);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 日志导出
     */
    @GetMapping("OperationLogExport")
    public void operationLogExport(@RequestParam String uuid, HttpServletResponse response,HttpServletRequest httpRequest){
        try {
            List<PermissionsDTO> arr = toolDao.permissionsViewCode(uuid);
            if (arr.get(0).getChangePermissions() == 1){
                List<Object> head = Arrays.asList("操作人ID","行为","操作地址","操作时间");
                List<OperationLogDTO> list = toolDao.operationLogAllCode();
                List<List<Object>> sheetDataList = new ArrayList<>();
                sheetDataList.add(head);
                for (OperationLogDTO str : list) {
                    List<Object> body = new ArrayList<>();
                    body.add(str.getUserId());
                    body.add(str.getAction());
                    body.add(str.getIpAddress());
                    body.add(str.getOperatingTime());
                    sheetDataList.add(body);
                }
                String ipAddress = httpRequest.getHeader("X-Real-Ip");
                if (StringUtils.isEmpty(ipAddress)) {
                    ipAddress = httpRequest.getRemoteAddr();
                }
                toolDao.operationLogAddCode(arr.get(0).getId(),"导出日志",ipAddress);
                ExcelUtils.export(response,"操作日志", sheetDataList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
