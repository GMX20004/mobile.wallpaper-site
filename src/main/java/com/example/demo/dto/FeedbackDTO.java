package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;

@Data
//意见反馈
public class FeedbackDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private int id;
    //类型
    private String type;
    //详细说明
    private String instructions;
    //是否需要回复0否1是
    private int is;
    //联系方式
    private String contact;
}
