package com.api.assignment.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum ResultTypes {
    /**
     * 성공
     */
    Success					( "200", 	200 ),

    Created					( "201", 	201 ),

    /**
     * 잘못된 요청
     */
    BadRequest				( "400", 	400 ),
    /**
     * 인증되지 않음
     */
    Unauthorized			( "401", 	401 ),
    /**
     * 거부됨
     */
    Forbidden				( "403", 	403 ),

    /**
     * 페이지를 찾을 수 없음
     */
    NotFound				( "404",	404 ),

    /**
     * 요청 만료시간 초과
     */
    RequestTimeout			( "408", 	408 ),

    SessionExpired			( "440",	440 ),

    /**
     * 서버 내부 오류
     */
    InternalError			( "500", 	500 ),


    Busy					( "504", 	504 ),

    ProtocolError			( "505", 	505 ),
    ;
    //@formatter:on

    private static final Map< Integer, ResultTypes > HTTP_STATUS_INDEX;
    private static final Map< String, ResultTypes > RESULT_CODE_INDEX;

    static {

        HTTP_STATUS_INDEX = new HashMap<>();
        RESULT_CODE_INDEX = new HashMap<>();

        for ( ResultTypes type : ResultTypes.values() ) {

            HTTP_STATUS_INDEX.put( type.httpStatus(), type );
            RESULT_CODE_INDEX.put( type.resultCode(), type );
        }
    }

    public static final ResultTypes findByCode( String code ) {

        return RESULT_CODE_INDEX.get( code );
    }

    public static final ResultTypes findByHttpStatus( int status ) {

        return HTTP_STATUS_INDEX.get( status );
    }

    /**
     * 코드
     */
    private String code;

    /**
     * Http 상태
     */
    private int status;

    /**
     * 생성자
     *
     * @param code 코드
     * @param status Http 상태
     */
    private ResultTypes(String code, int status ) {

        this.code = code;
        this.status = status;
    }

    /**
     * Http 상태를 가져온다.
     *
     * @return HttpStatus
     */
    public int httpStatus() {

        return status;
    }

    /**
     * 코드를 가져온다.
     *
     * @return String
     */
    public String resultCode() {

        return this.code;
    }
}
