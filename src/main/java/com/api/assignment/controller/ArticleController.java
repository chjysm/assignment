package com.api.assignment.controller;

import com.api.assignment.domain.dto.ArticleDto;
import com.api.assignment.service.ArticleService;
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
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private RestResponseFactory responseFactory = RestResponseFactory.getInstance();

    /**
     * 글 쓰기
     */
    @PostMapping("")
    @ApiOperation(value = "글 쓰기", notes = "필수 값 : 글 제목, 글 내용" )
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "articleTitle",value = "글 제목", required = true),
//            @ApiImplicitParam(name = "articleContents",value = "글 내용", required = true)
//    })
    public  @ResponseBody RestResponse write(HttpServletRequest request, HttpServletResponse response, @RequestBody ArticleDto articleDto ) throws Exception{

        String userNo = (String) request.getAttribute("userNo" );
        articleDto.setUserNo( userNo );

        Long articleId = articleService.articleWrite(articleDto);

        RestResponse results = responseFactory.create(request, response, articleId);
        return results;
    }

    /**
     * 글 수정
     */
    @PutMapping("")
    @ApiOperation(value = "글 수정", notes = "필수 값 : 글 아이디, 글 제목, 글 내용")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "articleId",value = "글 아이디", required = true),
//            @ApiImplicitParam(name = "articleTitle",value = "글 제목", required = true),
//            @ApiImplicitParam(name = "articleContents",value = "글 내용", required = true)
//    })
    public  @ResponseBody RestResponse modify(HttpServletRequest request, HttpServletResponse response, @RequestBody ArticleDto articleDto ) throws Exception{

        String userNo = (String) request.getAttribute("userNo" );
        articleDto.setUserNo( userNo );

        Long articleId = articleService.articleModify( articleDto );

        RestResponse results = responseFactory.create(request, response, articleId);
        return results;
    }

    /**
     * 글 조회
     */
    @GetMapping("/{articleId}")
    @ApiOperation(value = "글 조회")
    @ApiImplicitParam(name = "articleId",value = "글 ID", required = true)
    public  @ResponseBody RestResponse findOne(HttpServletRequest request, HttpServletResponse response, @PathVariable String articleId ) throws Exception{

        ArticleDto articleInfo = articleService.getArticleInfo( articleId );

        RestResponse results = responseFactory.create(request, response, articleInfo);
        return results;
    }

    /**
     * 글 삭제
     */
    @DeleteMapping("/{articleId}")
    @ApiOperation(value = "글 삭제")
    @ApiImplicitParam(name = "articleId",value = "글 ID", required = true)
    public  @ResponseBody RestResponse remove(HttpServletRequest request, HttpServletResponse response, @PathVariable String articleId ) throws Exception{

        int result = articleService.articleRemove(articleId);

        RestResponse results = responseFactory.create(request, response, result);
        return results;
    }
}
