package com.api.assignment.domain.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ArticleDto {
    @ApiModelProperty(example = "글 번호")
    private String articleId; // articleNo
    @ApiModelProperty(example = "글 제목")
    private String articleTitle;
    @ApiModelProperty(example = "글 내용")
    private String articleContents;
    @ApiModelProperty(example = "작성 사용자 번호")
    private String userNo;
    @ApiModelProperty(example = "댓글 번호 리스트")
    private List<String> commentsIdList;
}
