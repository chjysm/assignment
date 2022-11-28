package com.api.assignment.util;

import com.api.assignment.domain.enums.ResultTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.util.*;

public class RestResponse {


    /**
     * 요청 URI 경로
     */
    @ApiModelProperty(example = "요청 URI 경로")
    private String path;

    /**
     * 요청 질의 (요청 파라메터)
     */
    @ApiModelProperty(example = "요청 파라메터")
    private String query;

    /**
     * 요청 한 메소드
     */
    @ApiModelProperty(example = "요청 한 메소드")
    private String requestMethod;


    /**
     * 응답 속성 컨테이너
     */
    @ApiModelProperty(example = "응답 속성 컨테이너")
    private final Map< String, Object > attributes = new LinkedHashMap< String, Object >();

    /**
     * 응답 내용 (본문)
     */
    @ApiModelProperty(example = "응답 내용 (본문)")
    private Object content;

    /**
     * 응답 클래스 유형
     */
    @ApiModelProperty(example = "응답 클래스 유형")
    private Class< ? > contentType;

    /**
     * 응답 컬렉션 유형(응답이 컬렉션인 경우)
     */
    @ApiModelProperty(example = "응답 컬렉션 유형(응답이 컬렉션인 경우)")
    private Class< ? > collectionType;

    /**
     * 결과 유형
     */
    @ApiModelProperty(example = "결과 유형")
    private ResultTypes resultType;

    /**
     * 생성자
     */
    public RestResponse() {

        super();
    }

    /**
     * 생성자
     *
     * @param path 요청 URI 경로
     * @param query 요청 질의 (요청 파라메터)
     * @param content 응답 내용 (본문)
     * @param resultType 결과 유형
     */
    protected RestResponse( String path, String query, Object content, ResultTypes resultType ) {

        super();

        this.setPath( path );
        this.setQuery( query );
        this.content = content;
        this.resultType = resultType;
    }

    /**
     * 속성값을 가져온다.
     *
     * @param key 속성 키
     * @return 속성값
     */
    public Object getAttribute( String key ) {

        return attributes.get( key );
    }

    /**
     * 속성 키에 대한 목록(Enumeration)을 반환한다.
     *
     * @return 속성 키 값들
     */
    @JsonIgnore
    public Enumeration< String > getAttributeNames() {

        return Collections.enumeration( attributes.keySet() );
    }

    /**
     * 읽기 전용 속성맵을 가져온다.
     *
     * @return 속성 맵
     */
    public Map< String, Object > getAttributes() {

        return Collections.unmodifiableMap( attributes );
    }

    /**
     * 응답 내용(본문)을 가져온다.
     *
     * @return 응답 본문(내용)
     */
    public Object getContent() {

        return content;
    }

    /**
     * 응답 내용(본문)의 유형을 가져온다.
     *
     * @return 응답 내용의 클래스 이름 (클래스명만)
     */
    public String getContentSimpleType() {

        return ( contentType == null ) ? null : contentType.getSimpleName();
    }

    /**
     * 응답 내용이 {@link Collection}의 경우, 항목의 개수를 가져오며, 아닐 경우에는 null을 반환한다. <br />
     * null일 경우에는 Json 또는 xml의 엘리먼트를 생성하지 않는다.
     *
     * @return 항목 개수
     */
    @JsonInclude( JsonInclude.Include.NON_NULL )
    public Integer getNumberOfItems() {

        return ( content instanceof Collection ) ? ( ( Collection< ? > ) content ).size() : null;
    }

    /**
     * 요청 URI 경로를 가져온다.
     *
     * @return 요청 URI
     */
    public String getPath() {

        return path;
    }

    /**
     * 요청 파라메터를 가져온다.
     *
     * @return 요청 파라메터
     */
    @JsonInclude( JsonInclude.Include.NON_NULL )
    public String getQuery() {

        return query;
    }

    /**
     * 결과 코드를 가져온다.
     *
     * @return 결과 코드
     */
    public String getResultCode() {

        return resultType.resultCode();
    }


    /**
     * 속성을 설정한다.
     *
     * @param key 속성 키
     * @param value 속성 값
     */
    public void setAttribute( String key, Object value ) {

        attributes.put( key, value );
    }

    /**
     * 속성을 설정한다.
     *
     * @param attributes 속성 맵
     */
    public void setAttributes( Map< String, Object > attributes ) {

        if ( attributes == null ) {

            return;
        }

        attributes.putAll( attributes );
    }

    public void restAttributes() {

        attributes.clear();
    }

    /**
     * 응답 내용(본문)를 설정한다.
     *
     * @param content 응답 내용(본문)
     */
    public void setContent( Object content ) {

        this.content = content;

        if ( content == null ) {

            return;
        }

        if ( content instanceof Collection ) {

            Collection< ? > collection = ( Collection< ? > ) content;

            for ( Object item : collection ) {

                contentType = item.getClass();
                collectionType = content.getClass();

                return;
            }

        } else {

            contentType = content.getClass();
            collectionType = null;
        }
    }

    public String getCollectionType() {

        return ( collectionType == null ) ? null : collectionType.getName();
    }

    /**
     * 요청 URI 경로를 설정한다.
     *
     * @param path 요청 URI 경로
     */
    public void setPath( String path ) {

        this.path = path;
    }

    /**
     * 요청 파라매터를 가져온다.
     *
     * @param query 요청 파라메터
     */
    public void setQuery( String query ) {

        this.query = query;
    }

    /**
     * 결과 유형을 설정한다.
     *
     * @param resultType 결과 유형
     */
    public void setResultType( ResultTypes resultType ) {

        this.resultType = resultType;
    }

    @JsonIgnore
    public ResultTypes getResultType() {

        return this.resultType;
    }

    /**
     * Rest 로 요청한 HTTP 메소드를 가져온다.
     *
     * @return 요청한 HTTP 메소드
     */
    public String getRequestMethod() {

        return requestMethod;
    }

    /**
     * 요청한 HTTP 메소드를 설정한다.
     *
     * @param requestMethod HTTP 메소드
     */
    public void setRequestMethod( String requestMethod ) {

        this.requestMethod = requestMethod;
    }

}
