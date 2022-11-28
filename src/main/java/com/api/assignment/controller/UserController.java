package com.api.assignment.controller;

import com.api.assignment.domain.dto.UserDto;
import com.api.assignment.service.UserService;
import com.api.assignment.util.RestResponse;
import com.api.assignment.util.RestResponseFactory;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private RestResponseFactory responseFactory = RestResponseFactory.getInstance();

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    @ApiOperation(value = "사용자 회원가입", notes = "필수 값 : 사용자 ID, 사용자 비밀번호, 사용자 명")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId",value = "아이디", required = true),
//            @ApiImplicitParam(name = "userPwd",value = "패스워드", required = true),
//            @ApiImplicitParam(name = "userName",value = "이름", required = true)
//    })
    public  @ResponseBody RestResponse signup(HttpServletRequest request, HttpServletResponse response, @RequestBody UserDto user ) throws Exception{
        userService.join(user);
        RestResponse results = responseFactory.create(request, response);
        return results;
    }

    /**
     * 로그인
     */
    @PostMapping("/signin")
    @ApiOperation(value = "사용자 로그인", notes = "필수 값 : 사용자 ID, 사용자 비밀번호")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId",value = "아이디", required = true),
//            @ApiImplicitParam(name = "userPwd",value = "패스워드", required = true)
//    })
    public  @ResponseBody RestResponse signin(HttpServletRequest request, HttpServletResponse response, @RequestBody UserDto user ) throws Exception{
        String result = userService.login(user);
        RestResponse results = responseFactory.create(request, response, result);
        return results;
    }

    /**
     * 내 정보 조회
     */
    @GetMapping("/profile")
    @ApiOperation(value = "내 정보 조회")
    public  @ResponseBody RestResponse profile(HttpServletRequest request, HttpServletResponse response ) throws Exception{

        String userNo = (String) request.getAttribute("userNo" );

        UserDto userProfile = userService.getUserProfile(userNo);

        RestResponse results = responseFactory.create( request, response, userProfile );
        return results;
    }

    /**
     * 내 포인트 조회
     */
    @GetMapping("/points")
    @ApiOperation(value = "내 포인트 조회")
    public  @ResponseBody RestResponse points( HttpServletRequest request, HttpServletResponse response ) throws Exception{

        String userNo = (String) request.getAttribute("userNo" );

        UserDto userProfile = userService.getPoints(userNo);

        RestResponse results = responseFactory.create( request, response, userProfile );
        return results;
    }
}
