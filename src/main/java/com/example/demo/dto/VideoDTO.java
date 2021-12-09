package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;

@Data
//视频
public class VideoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private int id;
    //标题
    private String label;
    //文件类型
    private String type;
    //存储位置
    private String storageLocation;
}
