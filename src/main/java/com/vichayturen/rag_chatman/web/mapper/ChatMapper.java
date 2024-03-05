package com.vichayturen.rag_chatman.web.mapper;

import com.vichayturen.rag_chatman.pojo.entity.ChatEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMapper {
    @Select("select * from chat where user_id=#{userId} order by chat.create_time desc")
    List<ChatEntity> getAllChatByUserId(Long userId);
    @Select("select * from chat where id=#{chatId}")
    ChatEntity getChatByChatId(Long id);
    @Insert("insert into chat values (#{id},#{userId},#{libraryId},#{name},#{createTime})")
    void insertNewChat(ChatEntity chat);
}
