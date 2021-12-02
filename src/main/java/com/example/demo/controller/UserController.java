package com.example.demo.controller;


import com.example.demo.config.YmlConfig;
import com.example.demo.dao.ToolDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserDTO;
import com.example.demo.mod.ToolMod;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

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
            userDao.userTime(params);
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

    /**
     * 用户个人信息修改
     */
    @PostMapping("userModify")
    public Boolean userModify(
            @RequestParam(value = "file",required = false) MultipartFile file,                   //上传头像
            @RequestParam(value = "userId") int userId,                                          //用户id
            @RequestParam(value = "name",required = false) String name,                          //昵称
            @RequestParam(value = "instructions",required = false) String instructions,          //个人说明
            @RequestParam(value = "sex") String sex                                              //性别
    ){
        try {
                 Map<String,Object> param = new HashMap<>();
                 param.put("id",userId);
                 System.out.println(file);
                if (file!=null){
                    String path = ymlConfig.getWallpaperDisk()+"headPortrait";//存放路径
                    String fileName = file.getOriginalFilename();//获取文件名称
                    String suffixName=fileName.substring(fileName.lastIndexOf("."));//获取文件后缀
                    int num = userId;
                    fileName= num+suffixName;//重新生成文件名
                    File targetFile = new File(path);
                    if (!targetFile.exists()) {
                        // 判断文件夹是否未空，空则创建
                        targetFile.mkdirs();
                    }
                    File saveFile = new File(targetFile, fileName);
                    //指定本地存入路径
                    file.transferTo(saveFile);
                    param.put("img",fileName);
                }
                param.put("name",name);
                param.put("instructions",instructions);
                param.put("sex",sex);
                userDao.userModify(param);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
