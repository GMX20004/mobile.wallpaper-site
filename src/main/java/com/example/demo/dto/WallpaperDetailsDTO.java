package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
//壁纸详情表
public class WallpaperDetailsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //id
    private int id;
    //点赞数
    private int praise;
    //收藏数
    private int collection;
    //标题
    private String theTitle;
    //作者id
    private int userId;
    //作者头像
    private String headPortrait;
    //作者名称
    private String name;
    //标签
    private String theLabel;
    //图片类型
    private String type;
    //存储位置
    private String storageLocation;
    //是否点赞
    private int isPraise;
    //是否收藏
    private int isCollection;
    //创建时间
    private String creationTime;
    //文件大小
    private long size;
    //壁纸状态
    private int state;
}
