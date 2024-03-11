package com.vichayturen.rag_chatman.web.controller;

import com.vichayturen.rag_chatman.context.BaseContext;
import com.vichayturen.rag_chatman.pojo.vo.LibraryVo;
import com.vichayturen.rag_chatman.result.Result;
import com.vichayturen.rag_chatman.web.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/library")
@Slf4j
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping("addLibrary")
    public Result addLibrary(String libraryName, @RequestParam MultipartFile inputFile) {
        // TODO:实现方法
        Long userId = BaseContext.getCurrentId();
        String originalFilename = inputFile.getOriginalFilename();
        try {
            InputStream inputStream = inputFile.getInputStream();
            libraryService.addLibrary(userId, libraryName, inputStream);
            return Result.success();
        } catch (IOException e) {
            return Result.error(e.toString());
        }
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
