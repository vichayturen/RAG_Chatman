package com.vichayturen.rag_chatman.chatman_service.web.service.impl;

import com.vichayturen.rag_chatman.chatman_service.pojo.entity.LibraryEntity;
import com.vichayturen.rag_chatman.chatman_service.pojo.vo.LibraryVo;
import com.vichayturen.rag_chatman.chatman_service.document.DocumentLoader;
import com.vichayturen.rag_chatman.chatman_service.document.MixSearchClient;
import com.vichayturen.rag_chatman.chatman_service.document.TextSpliter;
import com.vichayturen.rag_chatman.chatman_service.exception.BaseException;
import com.vichayturen.rag_chatman.chatman_service.web.mapper.ChatMapper;
import com.vichayturen.rag_chatman.chatman_service.web.mapper.LibraryMapper;
import com.vichayturen.rag_chatman.chatman_service.web.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LibraryServiceImpl implements LibraryService {
    @Autowired
    private LibraryMapper libraryMapper;
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private MixSearchClient mixSearchClient;

    @Override
    @Transactional
    public void deleteLibrary(Long userId, Long libraryId) {
        LibraryEntity library = libraryMapper.getLibraryById(libraryId);
        if (!library.getUserId().equals(userId)) {
            throw new BaseException("删除失败！");
        }
        try {
            libraryMapper.deleteLibraryById(libraryId);
            chatMapper.deleteChatByLibraryId(libraryId);
            mixSearchClient.delete(library.getIndexName());
        } catch (IOException e) {
            throw new BaseException(e.toString());
        }
    }

    @Override
    public List<LibraryVo> getAllLibrary(Long userId) {
        List<LibraryVo> libraryVoList = libraryMapper.getAllLibraryByUserId(userId);
        return libraryVoList;
    }

    @Override
    public void addLibrary(Long userId, String libraryName, MultipartFile[] inputFiles) {
        String indexName = UUID.randomUUID().toString();
        try {
            for (MultipartFile inputFile : inputFiles) {
                InputStream inputStream = inputFile.getInputStream();
                String text = DocumentLoader.load(inputStream);
                List<String> chunks = TextSpliter.split(text);
                mixSearchClient.insert(chunks, indexName);
            }
        } catch (IOException e) {
            throw new BaseException(e.toString());
        }
        LibraryEntity library = LibraryEntity.builder()
                .userId(userId)
                .name(libraryName)
                .indexName(indexName)
                .createTime(LocalDateTime.now())
                .build();
        libraryMapper.addLibrary(library);
    }
}
