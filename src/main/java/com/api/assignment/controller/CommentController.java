package com.api.assignment.controller;

import com.api.assignment.domain.dto.ArticleDto;
import com.api.assignment.domain.dto.CommentDto;
import com.api.assignment.service.CommentService;
import com.api.assignment.util.RestResponse;
import com.api.assignment.util.RestResponseFactory;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private RestResponseFactory responseFactory = RestResponseFactory.getInstance();

    @PostMapping("")
    @ApiOperation(value = "댓글 작성", notes = "필수 값 : 글 ID, 댓글 내용")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "articleId",value = "글 ID", required = true),
//            @ApiImplicitParam(name = "commentsCotents",value = "댓글 내용", required = true)
//    })
    public  @ResponseBody RestResponse write(HttpServletRequest request, HttpServletResponse response, @RequestBody CommentDto commentDto ) throws Exception{

        String userNo = (String) request.getAttribute("userNo" );
        commentDto.setUserNo( userNo );

        CommentDto result = commentService.commentWrite(commentDto);

        RestResponse results = responseFactory.create(request, response, result);
        return results;
    }

    @PostMapping("/{commentsId}")
    @ApiOperation(value = "댓글 제거")
    @ApiImplicitParam(name = "commentsId",value = "댓글 ID", required = true)
    public  @ResponseBody RestResponse remove(HttpServletRequest request, HttpServletResponse response, @PathVariable String commentsId ) throws Exception{

        ArticleDto result = commentService.commentRemove(commentsId);

        RestResponse results = responseFactory.create(request, response, result);
        return results;
    }
}
