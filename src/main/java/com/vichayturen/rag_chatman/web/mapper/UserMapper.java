package com.vichayturen.rag_chatman.web.mapper;

import com.vichayturen.rag_chatman.pojo.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where email=#{email}")
    UserEntity getUserFromEmail(String email);

    @Select("select * from user where username=#{username}")
    UserEntity getUserFromUsername(String username);

    @Insert("insert into user values (#{id},#{email},#{username},#{password},#{createTime})")
    void addUser(UserEntity user);
}
