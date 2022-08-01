package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;

@Data
//获取访问数
public class PermissionsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private int id;
    //更改权限
    private int changePermissions;
    //修改用户信息
    private int modifyingUserInformation;
    //系统公告
    private int systemAnnouncement;
    //重要通知
    private int importantNotice;
    //重要通知系统内用户
    private int importantSystemUsers;
    //重要通知自定义人员
    private int importantNoticeCustom;
    //在线壁纸
    private int onlineWallpaper;
    //未上线壁纸
    private int notOnlineWallpaper;
    //在线壁纸信息修改
    private int onlineWallpaperModify;
    //在线壁纸标题
    private int onlineWallpaperModifyTitle;
    //在线壁纸标签
    private int onlineWallpaperModifyLabel;
    //在线壁纸状态
    private int onlineWallpaperModifyState;
    //在线壁纸位置
    private int onlineWallpaperModifyLocation;
    //未上线壁纸信息修改
    private int notOnlineWallpaperModify;
    //未上线壁纸标题
    private int notOnlineWallpaperModifyTitle;
    //未上线壁纸标签
    private int notOnlineWallpaperModifyLabel;
    //未上线壁纸状态
    private int notOnlineWallpaperModifyState;
    //未上线壁纸位置
    private int notOnlineWallpaperModifyLocation;
    //壁纸上传
    private int wallpaperUpload;
    //壁纸下载
    private int wallpaperDownload;
    //日志导出
    private int logExport;
    //日志查看
    private int logView;
    //重要通知次数
    private int messageNumber;
}
