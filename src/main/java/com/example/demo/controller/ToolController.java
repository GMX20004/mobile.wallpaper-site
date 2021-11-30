package com.example.demo.controller;


import com.example.demo.config.YmlConfig;
import com.example.demo.dao.ToolDao;
import com.example.demo.dao.WallpaperSortingDao;
import com.example.demo.dto.MessagesDTO;
import com.example.demo.dto.NumDTO;
import com.example.demo.dto.WallpaperDetailsDTO;
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
import java.io.File;
import java.io.IOException;
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
    private YmlConfig ymlConfig;
    ToolMod toolMod = new ToolMod();
    /**
     * 验证码图片
     */
    @GetMapping("yzm")
    public void verificationCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        ImageIO.write(image,"PNG",resp.getOutputStream());
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Email", value = "邮箱", paramType = "query",required = true, dataType="String"),
            @ApiImplicitParam(name = "Type", value = "类型", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "yzm", value = "验证码", paramType = "query",required = true,dataType="String")
    })
    public boolean mail(@ApiIgnore @RequestParam Map<String, Object> params){
        String text = toolMod.mail(params.get("Email").toString(),params.get("yzm").toString(),Integer.parseInt(params.get("Type").toString()));
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("1478588530@qq.com");
            helper.setTo(params.get("Email").toString());
            helper.setSubject("GXM");
            helper.setText(text, true);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 上传壁纸
     */
    @PostMapping("img")
    public Boolean img(
        @RequestParam("file") MultipartFile file,                                        //上传壁纸
            @RequestParam(value = "userId") int userId,                                  //用户id
            @RequestParam(value = "theTitle",required = false) String theTitle,          //标题
            @RequestParam(value = "theLabel") String theLabel                            //标签
            ){
        Map<String,Object> params = new HashMap<>();
        String uuid = toolMod.uuid();
        String path = ymlConfig.getWallpaperDisk()+"cs";//存放路径
        String fileName = file.getOriginalFilename();//获取文件名称
        String suffixName=fileName.substring(fileName.lastIndexOf("."));//获取文件后缀
        params.put("userId",userId);
        params.put("theTitle",theTitle);
        params.put("theLabel",theLabel);
        params.put("type",suffixName.substring(1));
        params.put("coding",uuid);
        wallpaperSortingDao.uploadWallpaperCode(params);
        fileName= wallpaperSortingDao.detailsWallpaperLinUuidCode(uuid)+suffixName;//重新生成文件名
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            // 判断文件夹是否未空，空则创建
            targetFile.mkdirs();
        }
        File saveFile = new File(targetFile, fileName);
        try {
            //指定本地存入路径
            file.transferTo(saveFile);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
            @ApiImplicitParam(name = "contact", value = "联系方式", paramType = "query",required = true, dataType="String")
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
}

