package com.vichayturen.rag_chatman.chatman_service.web.mapper;

import com.vichayturen.rag_chatman.chatman_service.pojo.entity.ChatEntity;
import com.vichayturen.rag_chatman.chatman_service.pojo.vo.ChatVo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ChatMapper {
    @Select("select * from chat where user_id=#{userId} order by chat.create_time desc")
    List<ChatVo> getAllChatByUserId(Long userId);

    @Select("select * from chat where id=#{chatId}")
    ChatEntity getChatByChatId(Long chatId);
    ChatVo getChatVoById(Long chatId);

    @Update("update chat set name=#{newName} where id=#{chatId}")
    void updateChat(Long chatId, String newName);

    @Delete("delete from chat where id=#{chatId}")
    void deleteChatById(Long chatId);

    @Insert("insert into chat (user_id, library_id, name, create_time) values (#{userId}, #{libraryId}, #{chatName}, #{createTime})")
    void addChat(Long userId, Long libraryId, String chatName, LocalDateTime createTime);

    @Delete("delete from chat where library_id=#{libraryId}")
    void deleteChatByLibraryId(Long libraryId);
}
