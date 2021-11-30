package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;

@Data
//各类型总数显示
public class NumDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户总数
    private int userNumber;
    //壁纸总数
    private int wallpaperNumber;
    //测试壁纸总数
    private int testWallpaperNumber;
    //意见反馈总数
    private int feedbackNumber;
}
