package com.vichayturen.rag_chatman.chatman_service.web.controller;

import com.vichayturen.rag_chatman.chatman_service.pojo.vo.LibraryVo;
import com.vichayturen.rag_chatman.chatman_service.result.Result;
import com.vichayturen.rag_chatman.chatman_service.context.BaseContext;
import com.vichayturen.rag_chatman.chatman_service.web.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/library")
@Slf4j
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping("addLibrary")
    public Result addLibrary(String libraryName, MultipartFile[] inputFiles) {
        Long userId = BaseContext.getCurrentId();
        log.info("添加图书馆：{}", libraryName);
        System.out.println(inputFiles.length);
        libraryService.addLibrary(userId, libraryName, inputFiles);
        return Result.success();
    }

    @PostMapping("deleteLibrary")
    public Result deleteLibrary(Long libraryId) {
        Long userId = BaseContext.getCurrentId();
        libraryService.deleteLibrary(userId, libraryId);
        return Result.success();
    }

    @GetMapping("/getAllLibrary")
    public Result<List<LibraryVo>> getAllLibrary() {
        Long userId = BaseContext.getCurrentId();
        List<LibraryVo> libraryVoList = libraryService.getAllLibrary(userId);
        return Result.success(libraryVoList);
    }
}
