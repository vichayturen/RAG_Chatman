package com.vichayturen.rag_chatman.web.service;

import com.vichayturen.rag_chatman.pojo.vo.LibraryVo;

import java.util.List;

public interface LibraryService {
    void deleteLibrary(Long userId, Long libraryId);
    List<LibraryVo> getAllLibrary(Long userId);
}
