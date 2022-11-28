package com.api.assignment.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDto {
    @ApiModelProperty(example = "댓글 번호")
    private String commentsId;
    @ApiModelProperty(example = "댓글 내용")
    private String commentsContents;
    @ApiModelProperty(example = "글 번호")
    private String articleId; // articleNo
    @ApiModelProperty(example = "댓글 작성자 번호")
    private String userNo;
}
