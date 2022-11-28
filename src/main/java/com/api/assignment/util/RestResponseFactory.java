package com.api.assignment.util;


import com.api.assignment.domain.enums.ResultTypes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.ref.WeakReference;
import java.util.Map;

public class RestResponseFactory {

    /**
     * 유일한 인스턴스
     */
    private static volatile RestResponseFactory instance = null;

    /**
     * 팩토리 인스턴스를 가져온다.
     *
     * @return 팩토리 인스턴스
     */
    public static RestResponseFactory getInstance() {

        if ( instance == null ) {

            synchronized ( RestResponseFactory.class ) {

                if ( instance == null ) {

                    instance = new RestResponseFactory();
                }
            }
        }

        return instance;
    }

    /**
     * 내용이 null인 성공 응답 객체를 생성한다.
     *
     * @param request 요청
     * @param response 응답
     * @return 생성된 응답 객체
     * @exception IllegalArgumentException request 또는 response가 null일 경우 발생한다.
     */
    public RestResponse create( HttpServletRequest request, HttpServletResponse response ) throws IllegalArgumentException {

        return this.create( request, response, null );
    }

    /**
     * 성공 응답 객체를 생성한다.
     *
     * @param request 요청
     * @param response 응답
     * @param content 응답 내용(본문)
     * @return 생성된 응답 객체
     * @exception IllegalArgumentException request 또는 response가 null일 경우 발생한다.
     */
    public RestResponse create( HttpServletRequest request, HttpServletResponse response, Object content ) throws IllegalArgumentException {

        return this.createResponse( request, response, null, content, ResultTypes.Success );
    }

    /**
     * 응답 객체를 생성한다.
     *
     * @param request 요청
     * @param response 응답
     * @param content 응답 내용(본문)
     * @param responseType 결과 유형
     * @return 생성된 응답 객체
     * @exception IllegalArgumentException request 또는 response가 null일 경우 발생한다.
     */
    public RestResponse create( HttpServletRequest request, HttpServletResponse response, Object content, ResultTypes responseType )
            throws IllegalArgumentException {

        return this.createResponse( request, response, null, content, responseType );
    }

    /**
     * 응답 객체를 생성한다.
     *
     * @param request 요청
     * @param response 응답
     * @param attributes 속성
     * @param content 응답 내용(본문)
     * @param responseType 결과 유형
     * @return 생성된 응답 객체
     * @exception IllegalArgumentException request 또는 response가 null일 경우 발생한다.
     */
    public RestResponse createResponse(	HttpServletRequest request,
                                           HttpServletResponse response,
                                           Map< String, Object > attributes,
                                           Object content,
                                           ResultTypes responseType ) throws IllegalArgumentException {

        if ( request == null ) {

            throw new IllegalArgumentException( "request is null." );
        }

        if ( response == null ) {

            throw new IllegalArgumentException( "response is null." );
        }

        RestResponse restResponse = new RestResponse();

        restResponse.setQuery( request.getQueryString() );
        restResponse.setPath( request.getRequestURI() );

        restResponse.setAttributes( attributes );

        if ( responseType == null ) {

            responseType = ResultTypes.Success;
        }

        restResponse.setContent( content );
        restResponse.setResultType( responseType );
        restResponse.setRequestMethod( request.getMethod() );

        response.setStatus( responseType.httpStatus() );
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        return new WeakReference<>( restResponse ).get();
    }
}
