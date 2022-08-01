package com.example.demo.body;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class PostHttpBody {

    @ApiModelProperty("链接地址")
    private String url;

    @ApiModelProperty("参数")
    private Map<String,Object> value;
}
