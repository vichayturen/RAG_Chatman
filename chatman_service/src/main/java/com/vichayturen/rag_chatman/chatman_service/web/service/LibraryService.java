package com.vichayturen.rag_chatman.chatman_service.web.service;

import com.vichayturen.rag_chatman.chatman_service.pojo.vo.LibraryVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LibraryService {
    void deleteLibrary(Long userId, Long libraryId);
    List<LibraryVo> getAllLibrary(Long userId);

    void addLibrary(Long userId, String libraryName, MultipartFile[] inputFiles);
}
