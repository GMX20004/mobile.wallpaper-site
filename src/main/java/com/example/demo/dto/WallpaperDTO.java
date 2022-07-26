package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
//壁纸表
public class WallpaperDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //壁纸id
    private int id;
    //图片类型
    private String type;
    //文件大小
    private int size;
}
