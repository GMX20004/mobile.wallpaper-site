package com.example.demo.controller;


import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.body.EmailBody;
import com.example.demo.body.UploadWallpaperBody;
import com.example.demo.config.YmlConfig;
import com.example.demo.dao.ToolDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dao.WallpaperSortingDao;
import com.example.demo.dto.*;
import com.example.demo.mod.ToolMod;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * 工具接口
 */
@RestController
@RequestMapping("/L")
public class ToolController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private WallpaperSortingDao wallpaperSortingDao;
    @Autowired
    private ToolDao toolDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private YmlConfig ymlConfig;
    ToolMod toolMod = new ToolMod();
    /**
     * 验证码图片
     */
    @GetMapping("yzm")
    public Map<String, String> verificationCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //服务器通知浏览器不要缓存
        resp.setHeader("pragma","no-cache");
        resp.setHeader("cache-control","no-cache");
        resp.setHeader("expires","0");
        //获取随机数
        //创建对象，在内存中图片(验证码对象)
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //美化图片
        //填充背景色
        Graphics g = image.getGraphics();//画笔对象
        g.setColor(Color.GRAY);
        g.fillRect(0,0,width,height);
        //产生4个随机验证码
        String checkCode = toolMod.verificationCode();
        //将验证码放入HttpSession中
        req.getSession().setAttribute("yzm",checkCode);
        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体",Font.BOLD,24));
        //向图片上写入验证码
        g.drawString(checkCode,15,25);
        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image,"PNG",stream);
        String base64 = null;
        base64 = Base64.encode(stream.toByteArray());
        stream.flush();
        stream.close();
        Map<String,String> map = new HashMap<>();
        map.put("img",base64);
        map.put("text",checkCode);
        return map;
    }

    /**
     * 四位随机验证码
     */
    @GetMapping("RandomNumber")
    public String randomNumber(){
        return toolMod.verificationCode();
    }

    /**
     * 发送邮件
     */
    @PostMapping("mail")
    public boolean mail(@RequestBody EmailBody emailBody,HttpServletRequest httpRequest){
        try {
            List<PermissionsDTO> arr = toolDao.permissionsViewCode(emailBody.getUuid());
            int num = arr.get(0).getMessageNumber();
            if(arr.get(0).getImportantNotice()==1 && num > 0){
                for(String i:emailBody.getRecipient()){
                    if(num > 0){
                        MimeMessage message = mailSender.createMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(message, true);
                        helper.setFrom("xiaoming2000mail@qq.com");
                        helper.setTo(i);
                        helper.setSubject("GXM");
                        helper.setText(emailBody.getContent(), true);
                        mailSender.send(message);
                        String ipAddress = httpRequest.getHeader("X-Real-Ip");
                        if (StringUtils.isEmpty(ipAddress)) {
                            ipAddress = httpRequest.getRemoteAddr();
                        }
                        toolDao.operationLogAddCode(arr.get(0).getId(),"发送邮件,接收人:"+i+"内容:"+emailBody.getContent(),ipAddress);
                    }
                    num--;
                }
                List<UserDTO> list = userDao.getUserUUIDCode(emailBody.getUuid());
                Map<String,Object> map = new HashMap<>();
                map.put("sql","message_number="+num);
                map.put("id",list.get(0).getId());
                toolDao.permissionsExtension(map);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 上传壁纸
     */
    @PostMapping("UploadWallpaper")
    public Map<String,Object> uploadWallpaper(UploadWallpaperBody uploadWallpaperBody,HttpServletRequest httpRequest){
        Map<String,Object> back = new HashMap<>();
        try {
            int num = wallpaperSortingDao.countCode()+1;
            String path = ymlConfig.getWallpaperDisk()+"cs";//存放路径
            String fileName = uploadWallpaperBody.getFile().getOriginalFilename();//获取文件名称
            String suffixName=fileName.substring(fileName.lastIndexOf("."));//获取文件后缀
            fileName = num+suffixName;//重新生成文件名
            File targetFile = new File(path);
            if (!targetFile.exists()) {
                // 判断文件夹是否未空，空则创建
                targetFile.mkdirs();
            }
            File saveFile = new File(targetFile, fileName);
            uploadWallpaperBody.getFile().transferTo(saveFile);
            Map<String,Object> map = new HashMap<>();
            map.put("id",num);
            map.put("theTitle",uploadWallpaperBody.getTheTitle());
            map.put("userId",uploadWallpaperBody.getUserId());
            map.put("theLabel",uploadWallpaperBody.getTheLabel());
            map.put("storageLocation","cs");
            map.put("type",suffixName.substring(1));
            map.put("size",uploadWallpaperBody.getSize());
            wallpaperSortingDao.uploadWallpaperCode(map);
            String ipAddress = httpRequest.getHeader("X-Real-Ip");
            if (StringUtils.isEmpty(ipAddress)) {
                ipAddress = httpRequest.getRemoteAddr();
            }
            List<WallpaperDetailsDTO> arr = wallpaperSortingDao.detailsWallpaperCode(num);
            back.put("data",arr.get(0));
            toolDao.operationLogAddCode(uploadWallpaperBody.getUserId(),"上传壁纸，壁纸编号为:"+num,ipAddress);
        }catch (Exception e){
            e.printStackTrace();
            back.put("state",false);
            return back;
        }
        back.put("state",true);
        return back;
    }
    /**
     * 总数查询
     */
    @GetMapping("number")
    public List<NumDTO>  number(){
        return toolDao.numberCode();
    }
    /**
     * 发送消息
     */
    @PostMapping("sendAMessage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "message", value = "消息", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "accept", value = "接受用户id", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "send", value = "发送用户id", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "level", value = "0普通消息，1管理员通知", paramType = "query",required = true, dataType="int")
    })
    public boolean sendAMessage(@ApiIgnore @RequestParam Map<String, Object> params){
        try {
            toolDao.sendAMessageCode(params);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     *提交意见反馈
     */
    @PostMapping("submitFeedback")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "instructions", value = "描述", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "is", value = "0否1是", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "contact", value = "联系方式", paramType = "query",required = false, dataType="String")
    })
    public boolean submitFeedback(@ApiIgnore @RequestParam Map<String, Object> params){
        int num = toolDao.submitFeedbackCode(params);
        if (num==1) return true;
        else return false;
    }
    /**
     * 获取消息
     */
    @PostMapping("receiveMessages")
    public List<MessagesDTO> receiveMessages(@RequestParam int id){
        List<MessagesDTO> arr = toolDao.receiveMessagesCode(id);
        for (int i=0;i<arr.size();i++){
            if (arr.get(i).getSend()==0){
                toolDao.deleteMessagesCode(arr.get(i).getId());
            }
        }
        return arr;
    }
    @PostMapping("toolCs")
    public String toolCs(){
        System.out.println(ymlConfig.getWallpaperDisk());
        return ymlConfig.getWallpaperDisk();
    }
    /**
     *当日访问数+1
     */
    @GetMapping("accessNumber")
    public void accessNumber(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        toolDao.accessNumberCode(sdf.format(date));
    }
    /**
     * 获取访问数
     */
    @GetMapping("obtainAccess")
    public List<AccessDTO> obtainAccess(@RequestParam int limit){
        return toolDao.obtainAccessCode(limit);
    }
    /**
     * 修改语言
     */
    @GetMapping("language")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "language", value = "语言", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "uuid", value = "唯一编码", paramType = "query",required = true,dataType="String")
    })
    public boolean language(@ApiIgnore @RequestParam Map<String, Object> params){
        if (toolDao.languageCode(params)==1) return true;
        else return false;
    }
}

