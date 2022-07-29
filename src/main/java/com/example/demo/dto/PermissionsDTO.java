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
    //壁纸信息
    private int wallpaper;
    //壁纸状态更改
    private int wallpaperState;
    //用户信息
    private int userInformation;
    //批量上传
    private int batchUpload;
    //批量下载
    private int BatchDownload;
    //系统公告
    private int systemAnnouncement;
    //重要通知
    private int ImportantNotice;
    //重要通知自定义人员
    private int ImportantNoticeCustom;
    //日志导出
    private int logExport;
}
