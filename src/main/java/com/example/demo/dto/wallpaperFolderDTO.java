package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;

@Data
//壁纸文件夹
public class wallpaperFolderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private int id;
    //文件夹名
    private String folder;
    //备注
    private String note;
}
