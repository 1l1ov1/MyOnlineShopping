package com.wan.controller;

import com.wan.entity.Comment;
import com.wan.result.Result;
import com.wan.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
@Slf4j
@Api("评论")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    @ApiOperation("添加评论")
    public Result<String> addComment(Comment comment) {
        log.info("添加评论 {}", comment);

        return Result.success("添加成功");
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除评论")
    public Result<String> deleteComment(List<Long> ids) {
        log.info("删除评论： {}", ids);
        return Result.success("删除成功");
    }
}
