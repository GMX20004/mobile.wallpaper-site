package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class User {
   // @TableId(type = IdType.ASSIGN_ID)
   //id
   private int id;
    //邮件
    private String mail;
    //密码
    private String password;
    //昵称
    private String name;
    //个人说明
    private String instructions;
    //性别
    private String sex;
    //获赞数
    private int praise;
    //粉丝数
    private int fans;
    //关注数
    private int focus_on;
    //投稿数
    private int contribute;
    //最近登入时间
    private Date recent_login;
    //创建时间
    private Date creation_time;
    //用户唯一编码
    private String user_id;

}
