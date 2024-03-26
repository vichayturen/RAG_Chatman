package com.vichayturen.rag_chatman.chatman_service.web.mapper;

import com.vichayturen.rag_chatman.chatman_service.pojo.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where email=#{email}")
    UserEntity getUserFromEmail(String email);

    @Select("select * from user where username=#{username}")
    UserEntity getUserFromUsername(String username);

    @Insert("insert into user values (#{id},#{email},#{username},#{password},#{createTime})")
    void addUser(UserEntity user);
}
