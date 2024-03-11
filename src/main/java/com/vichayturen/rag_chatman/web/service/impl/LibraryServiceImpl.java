package com.vichayturen.rag_chatman.web.service.impl;

import com.vichayturen.rag_chatman.document.DocumentLoader;
import com.vichayturen.rag_chatman.document.TextSpliter;
import com.vichayturen.rag_chatman.exception.BaseException;
import com.vichayturen.rag_chatman.pojo.entity.LibraryEntity;
import com.vichayturen.rag_chatman.pojo.vo.LibraryVo;
import com.vichayturen.rag_chatman.web.mapper.LibraryMapper;
import com.vichayturen.rag_chatman.web.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class LibraryServiceImpl implements LibraryService {
    @Autowired
    private LibraryMapper libraryMapper;

    @Override
    public void deleteLibrary(Long userId, Long libraryId) {
        LibraryEntity library = libraryMapper.getLibraryById(libraryId);
        if (!Objects.equals(library.getUserId(), userId)) {
            throw new BaseException("删除失败！");
        }
        libraryMapper.deleteLibraryById(libraryId);
    }

    @Override
    public List<LibraryVo> getAllLibrary(Long userId) {
        List<LibraryVo> libraryVoList = libraryMapper.getAllLibraryByUserId(userId);
        return libraryVoList;
    }

    @Override
    public void addLibrary(Long userId, String libraryName, InputStream inputStream) {
        String text = DocumentLoader.load(inputStream);
        System.out.println(text);
        List<String> chunks = TextSpliter.split(text);
        LibraryEntity library = LibraryEntity.builder()
                .userId(userId)
                .name(libraryName)
                .path(UUID.randomUUID().toString())
                .createTime(LocalDateTime.now())
                .build();
//        libraryMapper.addLibrary(library);
    }
}
