package com.example.demo.body;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadWallpaperBody {

    @ApiModelProperty("头像文件")
    private MultipartFile file;

    @ApiModelProperty("用户ID")
    private int userId;

    @ApiModelProperty("标题")
    private String theTitle;

    @ApiModelProperty("标签")
    private String theLabel;

    @ApiModelProperty("文件大小")
    private long size;

}
