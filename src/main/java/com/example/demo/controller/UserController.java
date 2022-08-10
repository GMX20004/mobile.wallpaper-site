package com.example.demo.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.body.EmailBody;
import com.example.demo.body.UserModifyBody;
import com.example.demo.config.YmlConfig;
import com.example.demo.dao.ToolDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.PermissionsDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.mod.ToolMod;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关查询
 */
@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private ToolDao toolDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private YmlConfig ymlConfig;
    ToolMod toolMod = new ToolMod();
    /**
     * 登入
     */
    @PostMapping("logInTo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query",required = true, dataType="String")
    })
    public List logInTo(@ApiIgnore @RequestParam Map<String, Object> params)  {
        List arr = userDao.getLogInToCode(params);
        if (arr!=null&&arr.size()!=0){
            params.put("time",toolMod.time());
            System.out.println(params.get("time"));
            userDao.userTimeCode(params);
        }else {
            arr.add(false);
        }
        return arr;
    }

    /**
     * 注册
     */
    @PostMapping("registered")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "width", value = "分辨率宽度", paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "height", value = "分辨率高度", paramType = "query", dataType="int")
    })
    public List registered(@ApiIgnore @RequestParam Map<String, Object> params)  {
        int num = userDao.getEmailCode(params);
        List arr = new ArrayList();
        if (num==1){
            arr.add(false);
            arr.add("账号已存在");
        }else{
            params.put("uuid",toolMod.uuid());
            params.put("time",toolMod.time());
            num = userDao.getRegisteredCode(params);
            if (num==1){
                arr.add(true);
                arr.add("账号创建成功");
            }else{
                arr.add(false);
                arr.add("账号创建失败，请联系管理员处理");
            }
        }
        return arr;
    }

    /**
     * 忘记密码
     */
    @PostMapping("forgetPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query",required = true, dataType="String")
    })
    public List forgetPassword(@ApiIgnore @RequestParam Map<String, Object> params)  {
        int num = userDao.getEmailCode(params);
        List arr = new ArrayList();
        if (num==1){
            num = userDao.getModifyCode(params);
            if (num==1){
                arr.add(true);
                arr.add("密码修改成功");
            }else{
                arr.add(false);
                arr.add("修改失败,请查看邮箱是否改动或联系管理员");
            }
        }else{
            arr.add(false);
            arr.add("账号不存在");
        }
        return arr;
    }

    /**
     * 判断email是否存在
     */
    @PostMapping("whether")
    @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query",required = true, dataType="String")
    public boolean whether(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = userDao.getEmailCode(params);
        if (num==1) return true;
        else return false;
    }

    //查看用户信息
    @PostMapping("user")
    @ApiImplicitParam(name = "用户唯一id", value = "id", paramType = "query",required = true, dataType="int")
    public List<UserDTO> user(@ApiIgnore @RequestParam Map<String, Object> params){
        List<UserDTO> arr = userDao.getUserCode(params);
        return arr;
    }
    //查看用户信息
    @PostMapping("userUUID")
    public List<UserDTO> userUUID(@RequestBody EmailBody emailBody,HttpServletRequest httpRequest){
        List<UserDTO> arr = userDao.getUserUUIDCode(emailBody.getUuid());
        String ipAddress = httpRequest.getHeader("X-Real-Ip");
        if (StringUtils.isEmpty(ipAddress)) {
            ipAddress = httpRequest.getRemoteAddr();
        }
        toolDao.operationLogAddCode(arr.get(0).getId(),"用户登录",ipAddress);
        return arr;
    }
    /**
     * 用户个人信息修改
     */
    @PostMapping("userModify")
    public Boolean userModify(UserModifyBody userModifyBody, HttpServletRequest httpRequest){
        try {
            List<UserDTO> arr = userDao.getUserUUIDCode(userModifyBody.getUuid());
            List<PermissionsDTO> permissions = toolDao.permissionsViewCode(userModifyBody.getUuid());
            if (arr.size() != 0){
                if (arr.get(0).getId()==userModifyBody.getUserId() || permissions.get(0).getModifyingUserInformation()==1){
                    Map<String,Object> param = new HashMap<>();
                    param.put("id",userModifyBody.getUserId());
                    if (userModifyBody.getFile()!=null){
                        String path = ymlConfig.getWallpaperDisk()+"headPortrait";//存放路径
                        String fileName = userModifyBody.getFile().getOriginalFilename();//获取文件名称
                        String suffixName=fileName.substring(fileName.lastIndexOf("."));//获取文件后缀
                        fileName= userModifyBody.getUserId()+suffixName;//重新生成文件名
                        File targetFile = new File(path);
                        if (!targetFile.exists()) {
                            // 判断文件夹是否未空，空则创建
                            targetFile.mkdirs();
                        }
                        File saveFile = new File(targetFile, fileName);
                        //指定本地存入路径
                        userModifyBody.getFile().transferTo(saveFile);
                        param.put("img",fileName);
                    }
                    param.put("name",userModifyBody.getName());
                    param.put("instructions",userModifyBody.getInstructions());
                    param.put("sex",userModifyBody.getSex());
                    userDao.userModifyCode(param);
                    String ipAddress = httpRequest.getHeader("X-Real-Ip");
                    if (StringUtils.isEmpty(ipAddress)) {
                        ipAddress = httpRequest.getRemoteAddr();
                    }
                    toolDao.operationLogAddCode(arr.get(0).getId(),"用户"+userModifyBody.getUserId()+"的信息修改",ipAddress);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
