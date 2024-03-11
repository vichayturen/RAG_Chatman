package com.vichayturen.rag_chatman.web.mapper;

import com.vichayturen.rag_chatman.pojo.entity.LibraryEntity;
import com.vichayturen.rag_chatman.pojo.vo.LibraryVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LibraryMapper {
    @Select("select * from library where id=#{libraryId}")
    LibraryEntity getLibraryById(Long libraryId);

    @Delete("delete from library where id=#{libraryId}")
    void deleteLibraryById(Long libraryId);

    @Select("select id, name from library where user_id=#{userId}")
    List<LibraryVo> getAllLibraryByUserId(Long userId);

    @Insert("insert into library (user_id, path, name, create_time) values (#{userId}, #{path}, #{name}, #{createTime})")
    void addLibrary(LibraryEntity library);
}
