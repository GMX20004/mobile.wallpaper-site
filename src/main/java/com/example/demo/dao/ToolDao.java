package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ToolDao extends BaseMapper<UserDTO> {
    //总数查询
    @Select("SELECT \n" +
            "COUNT(*) AS user_number,\n" +
            "(SELECT COUNT(*) FROM wallpaper) AS wallpaper_number,\n" +
            "(SELECT COUNT(*) FROM wallpaper_lins) AS testWallpaper_number,\n" +
            "(SELECT COUNT(*) FROM feedback) AS feedback_number\n" +
            "FROM\n" +
            "`user`")
    List<NumDTO> numberCode();
    //意见反馈查看
    @Select("SELECT * FROM feedback")
    List<FeedbackDTO> feedbackCode();
    //发送消息
    @Insert("INSERT INTO message(message,accept,send,`level`)VALUES(#{message},#{accept},#{send},#{level})")
    int sendAMessageCode(Map<String,Object> param);
    //删除反馈
    @Delete("DELETE FROM feedback WHERE id = #{id} ")
    int deleteFeedbackCode(Map<String,Object> param);
    @Insert("INSERT INTO `feedback`(type,instructions,`is`,contact)VALUES(#{type},#{instructions},#{is},#{contact})")
    int submitFeedbackCode(Map<String,Object> param);
    //获取消息
    @Select("SELECT t1.*,t2.`name` FROM message t1 LEFT JOIN `user` t2 ON t1.send=t2.id WHERE t1.accept=#{id} and send!=0")
    List<MessagesDTO> receiveMessagesCode(int id);
    //删除消息
    @Delete("DELETE FROM message WHERE id = #{id}")
    int deleteMessagesCode(int id);
    //获取管理员消息
    @Select("SELECT * FROM message WHERE accept=#{id} and send=0")
    List<MessagesDTO> receiveAdminMessagesCode(int id);
    //壁纸文件夹
    @Select("SELECT * FROM wallpaper_folder")
    List<wallpaperFolderDTO> wallpaperFolderCode();
    //新增壁纸文件夹
    @Insert("INSERT INTO wallpaper_folder(folder,note)VALUES(#{folder},#{note})")
    int wallpaperFolderInsertCode(Map<String,Object> param);
}
