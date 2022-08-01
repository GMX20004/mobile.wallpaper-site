package com.example.demo.body;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmailBody {

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("收件人")
    private String[] recipient;

    @ApiModelProperty("发送者uuid唯一编码")
    private String uuid;
}
