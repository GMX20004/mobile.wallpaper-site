package com.example.demo.body;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
//获取系统公告
public class AnnouncementBody {

    @ApiModelProperty("内容")
    private String[] content;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("图片文件")
    private MultipartFile[] fileList;

    @ApiModelProperty("唯一编码")
    private String uuid;
}
