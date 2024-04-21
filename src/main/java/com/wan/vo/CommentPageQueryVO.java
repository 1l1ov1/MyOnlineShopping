package com.wan.vo;

import com.wan.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageQueryVO {

    private Long total;

    private List<Comment> comments;
}
