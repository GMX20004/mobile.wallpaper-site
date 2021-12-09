package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dto.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoDao extends BaseMapper<UserDTO> {
    //查询视频总数
    @Select("SELECT count(*) FROM `video`")
    int numberCode();
    //视频列表显示
    @Select("SELECT * FROM `video` ORDER BY sorting LIMIT #{start},10")
    List<VideoDTO> listCode(int start);
}
