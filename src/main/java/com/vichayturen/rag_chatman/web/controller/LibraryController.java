package com.vichayturen.rag_chatman.web.controller;

import com.vichayturen.rag_chatman.context.BaseContext;
import com.vichayturen.rag_chatman.pojo.vo.LibraryVo;
import com.vichayturen.rag_chatman.result.Result;
import com.vichayturen.rag_chatman.web.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping("addLibrary")
    public Result addLibrary(String libraryName) {
        // TODO:实现方法
        Long userId = BaseContext.getCurrentId();
        libraryService.addLibrary(userId, libraryName, files);
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
