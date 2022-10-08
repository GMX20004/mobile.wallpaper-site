package com.example.demo.result;


import java.util.HashMap;
import java.util.Map;

public class ResultData <T> {
    //返回状态码
    private Integer status;
    //返回消息
    private String message;
    //返回数据
    private T data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    //Success结果
    public static  <T> ResultData<T> success(T data){
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(RequestCode.SUCCESS.getCode());
        resultData.setMessage(RequestCode.SUCCESS.getMessage());
        resultData.setData(data);
        return resultData;
    }

    //Fail结果
    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }
    public <T> ResultData<T> custom(int code, String message,T data){
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        resultData.setData(data);
        return resultData;
    }
}


