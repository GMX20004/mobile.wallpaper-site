package com.example.demo.body;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserModifyBody {

    @ApiModelProperty("头像文件")
    private MultipartFile file;

    @ApiModelProperty("用户ID")
    private int userId;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("个人说明")
    private String instructions;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("uuid唯一编码")
    private String uuid;

    @ApiModelProperty("操作原因")
    private String reason;
}
