package com.vichayturen.rag_chatman.web.service;

import com.vichayturen.rag_chatman.pojo.vo.LibraryVo;

import java.io.InputStream;
import java.util.List;

public interface LibraryService {
    void deleteLibrary(Long userId, Long libraryId);
    List<LibraryVo> getAllLibrary(Long userId);

    void addLibrary(Long userId, String libraryName, InputStream inputStream);
}
