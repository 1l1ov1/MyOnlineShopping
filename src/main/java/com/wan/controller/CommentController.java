package com.wan.controller;

import com.wan.constant.RedisConstant;
import com.wan.entity.Comment;
import com.wan.result.Result;
import com.wan.service.CommentService;
import com.wan.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@Slf4j
@Api("评论")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @GetMapping("/hidden/{id}")
    @ApiOperation("隐藏评论")
    public Result<String> hiddenComment(@PathVariable Long id, @RequestParam Integer status) {
        log.info("隐藏评论 {} {}", id, status);
        Long storeId = commentService.hiddenComment(id, status);
        // 然后清空缓存
        /*RedisUtils.clearRedisCache(redisTemplate,
                RedisConstant.STORE_COMMENT + "all-" + storeId,
                RedisConstant.STORE_COMMENT + "goods-" + storeId,
                RedisConstant.STORE_COMMENT + "bad-" + storeId);*/
        RedisUtils.clearRedisCacheByPattern(redisTemplate, RedisConstant.STORE_COMMENT + "*");
        return Result.success("修改成功");
    }

    @DeleteMapping("/delete")
    @ApiOperation("批量删除评论")
    public Result<String> deleteComment(@RequestParam  List<Long> ids) {
        log.info("批量删除评论 {}", ids);
        commentService.batchDeleteComment(ids);
        // 删除缓存
        RedisUtils.clearRedisCacheByPattern(redisTemplate, RedisConstant.STORE_COMMENT + "*");
        return Result.success("删除成功");
    }
}
