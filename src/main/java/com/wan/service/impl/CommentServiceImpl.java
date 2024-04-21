package com.wan.service.impl;

import com.wan.entity.Comment;
import com.wan.mapper.CommentMapper;
import com.wan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void addComment(Comment comment) {

    }

    @Override
    public void batchDeleteComment(List<Long> ids) {

    }

    @Override
    public void updateComment(Comment comment) {

    }
}
