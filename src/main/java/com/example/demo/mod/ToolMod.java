package com.example.demo.mod;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Random;

/**
 * 工具类
 */
public class ToolMod {

    //生成四位字母数字组合的验证码
    public String verificationCode(){
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int size = base.length();
        Random r = new Random();
        StringBuffer su = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            su.append(c);
        }

        return su.toString();
    }
    //生成第一无二的id
    public String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
    //邮件发送内容
    public String mail(String email,String yzm,int a){
        String b = null;
        switch (a){
            case 1:{
                b=  "    <p>亲爱的<b>"+email+"</b>, 欢迎加入 GXM !</p>\n" +
                    "    <p>当您收到这封信的时候，您已经可以正常登录了。</p>\n" +
                    "    <p>请点击链接登录首页: <a href='http://www.gxm2000.cn'>http://www.gxm2000.cn</a></p>\n" +
                    "    <p>如果您的 Email 程序不支持链接点击，请将上面的地址拷贝至您的浏览器的地址栏进入。</p>\n" +
                    "    <p>网站暂不支持PC端,我们对您产生的不便，深表歉意。</p>\n" +
                    "    <p>希望您在 GXM 度过快乐的时光!</p>\n" +
                    "    <p></p>\n" +
                    "    <p>-----------------------</p>\n" +
                    "    <p></p>\n" +
                    "    <p>(这是一封自动产生的Email，请勿回复。)</p>\n";
            }break;
            case 2:{
                b="      <p>亲爱的<b>"+email+"</b>用户</p>\n"+
                  "      <p>这是本次验证码:<b>"+yzm+"</b></p>\n"+
                  "      <p>请勿外传</p>"+
                  "      <p>-----------------------</p>\n" +
                  "      <p></p>\n" +
                  "      <p>(这是一封自动产生的Email，请勿回复。)</p>\n";
            }break;


        }
        return b;
    }
    //每日壁纸显示位置更新随机数
    public int randomDigital(int max){
        int min=1;
        int ran2 = (int) (Math.random()*(max+1-min)+min);
        return ran2;
    }
    //文件转移
    public boolean imgTransfer(String target,String destination){
        boolean is = true;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(target));//输入缓冲区
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destination));//缓冲区输出
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bis.read(bytes))!=-1){
                bos.write(bytes);
            }
            bis.close();
            bos.close();
            deleteFile(target);
        }catch (Exception e){
            e.printStackTrace();
            is=false;
        }

        return is;
    }
    //文件删除
    public boolean deleteFile(String fileName){
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }
    //获取当前时间
    public String time(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dates = sdf.format(date);
        return dates;
    }
    public int randomNumber(int scope){
        Random r = new Random();
        return r.nextInt(scope);
    }
    public static void main(String[] args) {
       String a = "123456.jpgggggg";
        String suffixName=a.substring(a.lastIndexOf("."));
       System.out.println(suffixName.substring(1));
    }
}
