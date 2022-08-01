package com.example.demo.body;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class PermissionsBody {

    @ApiModelProperty("用户id")
    private int id;
    @ApiModelProperty("更改权限")
    private int changePermissions;
    @ApiModelProperty("修改用户信息")
    private int modifyingUserInformation;
    @ApiModelProperty("系统公告")
    private int systemAnnouncement;
    @ApiModelProperty("重要通知")
    private int importantNotice;
    @ApiModelProperty("重要通知系统内用户")
    private int importantSystemUsers;
    @ApiModelProperty("重要通知自定义人员")
    private int importantNoticeCustom;
    @ApiModelProperty("在线壁纸")
    private int onlineWallpaper;
    @ApiModelProperty("未上线壁纸")
    private int notOnlineWallpaper;
    @ApiModelProperty("在线壁纸信息修改")
    private int onlineWallpaperModify;
    @ApiModelProperty("在线壁纸标题")
    private int onlineWallpaperModifyTitle;
    @ApiModelProperty("在线壁纸标签")
    private int onlineWallpaperModifyLabel;
    @ApiModelProperty("在线壁纸状态")
    private int onlineWallpaperModifyState;
    @ApiModelProperty("在线壁纸位置")
    private int onlineWallpaperModifyLocation;
    @ApiModelProperty("未上线壁纸信息修改")
    private int notOnlineWallpaperModify;
    @ApiModelProperty("未上线壁纸标题")
    private int notOnlineWallpaperModifyTitle;
    @ApiModelProperty("未上线壁纸标签")
    private int notOnlineWallpaperModifyLabel;
    @ApiModelProperty("未上线壁纸状态")
    private int notOnlineWallpaperModifyState;
    @ApiModelProperty("未上线壁纸位置")
    private int notOnlineWallpaperModifyLocation;
    @ApiModelProperty("壁纸上传")
    private int wallpaperUpload;
    @ApiModelProperty("壁纸下载")
    private int wallpaperDownload;
    @ApiModelProperty("日志导出")
    private int logExport;
    @ApiModelProperty("日志查看")
    private int logView;
    @ApiModelProperty("操作人")
    private String uuid;
}
