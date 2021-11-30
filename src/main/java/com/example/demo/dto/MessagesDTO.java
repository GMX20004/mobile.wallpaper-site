package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;

@Data
//消息表
public class MessagesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //唯一id
    private int id;
    //消息
    private String message;
    //接收用户id
    private int accept;
    //发送用户id
    private int send;
    //发送用户昵称
    private String name;
    //0普通消息，1管理员通知
    private int level;
}
