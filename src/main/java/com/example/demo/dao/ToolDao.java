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
            "(SELECT COUNT(*) FROM wallpaper WHERE state=2) AS wallpaper_number,\n" +
            "(SELECT COUNT(*) FROM wallpaper WHERE state=0 OR state=1) AS testWallpaper_number,\n" +
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
    @Select("SELECT t1.*,t2.`name` FROM message t1 LEFT JOIN `user` t2 ON t1.send=t2.id WHERE t1.accept=#{id} and send!=0 and `read` = 0")
    List<MessagesDTO> receiveMessagesCode(int id);
    //删除消息
    @Delete("DELETE FROM message WHERE id = #{id}")
    int deleteMessagesCode(int id);
    @Update("UPDATE message SET `read` = 1 WHERE id= #{id} ")
    int updateMessageCode(int id);
    //获取管理员消息
    @Select("SELECT * FROM message WHERE accept=#{id} and send=0 and `read` = 0")
    List<MessagesDTO> receiveAdminMessagesCode(int id);
    //壁纸文件夹
    @Select("SELECT * FROM wallpaper_folder")
    List<wallpaperFolderDTO> wallpaperFolderCode();
    //新增壁纸文件夹
    @Insert("INSERT INTO wallpaper_folder(folder,note)VALUES(#{folder},#{note})")
    int wallpaperFolderInsertCode(Map<String,Object> param);
    //跨域链接存储
    @Insert("INSERT INTO domain (url,value,type,results,time)VALUES(#{url},#{value},#{type},#{results},#{time})")
    int domainUrlCode(Map<String,Object> param);
    //任务信息表
    @Insert("INSERT INTO task_information (task_name,task_information,create_time)VALUES(#{name},#{information},#{time})")
    int taskInformation(Map<String,Object> param);
    //定时任务查询
    @Select("SELECT * FROM `cron_task` WHERE cron_state = 1")
    List<CronDTO> cronView();
    //定时任务修改
    @Update("UPDATE `cron_task` SET cron_link=#{link},link_type=#{type},link_value=#{value},regular_time=#{time},cron_state=#{state} WHERE id = #{id}")
    int cronModify(Map<String,Object> param);
    //定时任务创建
    @Insert("INSERT INTO `cron_task` (cron_link,link_type,link_value,regular_time,cron_state)VALUES(#{link},#{type},#{value},#{time},#{state})")
    int cronCreate(Map<String,Object> param);
    //定时任务删除
    @Delete("DELETE FROM `cron_task` WHERE id = #{id}")
    int cronDelete(Map<String,Object> param);
    // 新增访问数
    @Insert("INSERT INTO access (date_time) VALUES (#{date})")
    int accessCode(String date);
    // 当日访问数+1
    @Update("UPDATE access SET access_number=access_number+1 WHERE date_time = #{date}")
    int accessNumberCode(String date);
    // 获取访问数
    @Select("SELECT * FROM access ORDER BY id DESC LIMIT 0,#{limit}")
    List<AccessDTO> obtainAccessCode(int limit);
}
