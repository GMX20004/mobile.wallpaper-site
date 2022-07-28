package com.example.demo.body;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModifyAuditBody {

    @ApiModelProperty("壁纸id")
    private int id;

    @ApiModelProperty("壁纸标题")
    private String theTitle;

    @ApiModelProperty("壁纸标签")
    private String theLabel;

    @ApiModelProperty("壁纸存储文件位置")
    private String storageLocation;

    @ApiModelProperty("壁纸状态")
    private int state;

    @ApiModelProperty("uuid唯一编码")
    private String uuid;
}
