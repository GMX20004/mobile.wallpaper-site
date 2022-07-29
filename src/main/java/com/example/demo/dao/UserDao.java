package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dto.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 用户
 */
@Mapper
public interface UserDao extends BaseMapper<UserDTO> {
    //登录判断
    //mysql8.0及以上不支持PASSWORD函数,需要用SHA1函数代替
    @Select(" SELECT *  FROM `user` WHERE email = #{email} and `password`=SHA1(#{password}) and identity=0")
    List<UserDTO> getLogInToCode(Map<String,Object> param);
    //判断是否已存在用户
    @Select(" SELECT COUNT(*) AS num FROM `user` WHERE email = #{email}")
    int getEmailCode(Map<String,Object> param);
    //查询用户信息
    @Select("SELECT * FROM `user` WHERE id=#{id}")
    List<UserDTO> getUserCode(Map<String,Object> param);
    @Select("SELECT * FROM `user` WHERE user_id=#{uuid}")
    List<UserDTO> getUserUUIDCode(String uuid);
    //新建用户
    @Insert("INSERT INTO `user`(email,`password`,user_id,creation_time,width,height)VALUES(#{email},SHA1(#{password}),#{uuid},#{time},#{width},#{height})")
    int getRegisteredCode(Map<String,Object> param);
    //用户修改密码
    @Update("UPDATE `user` SET `password` = SHA1(#{password}) WHERE email = #{email}")
    int getModifyCode(Map<String,Object> param);
    //查询所有用户信息--管理员权限
    @Select("SELECT * FROM `user` WHERE identity=#{identity} ORDER BY id LIMIT #{start},#{limit}")
    List<UserDTO> userViewCode(Map<String,Object> param);
    //查询所有用户数量--管理员权限
    @Select("SELECT COUNT(*) FROM `user` WHERE identity=#{identity} ORDER BY id LIMIT #{start},#{limit}")
    int userNumCode(Map<String,Object> param);
    //删除用户--管理员权限
    @Delete("DELETE FROM `user` WHERE id=#{id}")
    int deleteUserCode(Map<String,Object> param);
    //更改用户状态--管理员权限
    @Update("UPDATE `user` SET state=#{state} WHERE id=#{id}")
    int stateUserCode(Map<String,Object> param);
    //更新最近登入时间
    @Update("UPDATE `user` SET recent_login=#{time} WHERE email=#{email}")
    int userTimeCode(Map<String,Object> param);
    //用户收藏点赞总数修改
    void userAddDeleteCGCode(Map<String,Object> param);
    //用户信息修改
    void userModifyCode(Map<String,Object> param);
    //用户投稿增加
    @Update("UPDATE `user` SET contribute=contribute+1 WHERE id = #{userId}")
    int userContributeCode(int userId);
    //修改用户唯一编码
    @Update("UPDATE `user` SET user_id=#{uuid} WHERE id=#{id}")
    int userUpdateUuidCode(Map<String,Object> param);
    //查看用户唯一编码
    @Select("SELECT COUNT(*) FROM `user` WHERE user_id=#{uuid} AND identity=0")
    int userUuidCode(Map<String,Object> param);
}
