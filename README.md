# assignment

Swagger 주소  : http://localhost:8080/swagger-ui.html

구현 방법     : Spring Security + JWT 와 JPA 로 RestFul API 구현
테스트 방법   : 1. H2DB 생성
                    1) Embeded H2 DB 생성 후   : jdbc:h2:~/assignment
                    2) Sever H2 DB 생성       :  jdbc:h2:tcp://localhost/~/assignment
                2. 프로젝트 구동 
                3. Swagger 접속 후 테스팅 진행
                4. 로그인, 회원가입을 제외한 나머지 API 는 헤더 에 로그인 결과로 나오는 JwtToken 토큰 설정이 필요합니다.
                   ![image](https://user-images.githubusercontent.com/50863392/204238611-82e80188-5ddb-47dc-855e-b3b11367d265.png)
                   
테스트 결과   : 구현 요구 사항 모두 정상 동작 확인하였습니다.

구현 요구 사항
   - 회원 가입 API
        url           :    /user/signup
        method        :    POST
        필수 파라미터
            아이디   : userId (String)
            패스워드 : userPwd (String)
            이름     : userName (String)
        파라미터 예시
            {
                "userId":"userid",
                "userPwd":"passw0rd",
                "userName":"username"
            }           
        
 - 로그인 API (accessToken 발급해서 응답으로 반환)
        url           :    /user/signin
        method        :    POST
        필수 파라미터
            아이디   : userId (String)
            패스워드 : userPwd (String)
        파라미터 예시
            {
                "userId":"userid",
                "userPwd":"passw0rd"
            }

 - 내 정보 조회 API
        url           :     /user/profile
        method        :    GET
        필수 파라미터
            accessToken (JWT) : header
        파라미터 예시
            -header 'Authorization: JwtToken'
            
 - 내 Points 조회 API
        url           :    /user/points
        method        :    GET
        필수 파라미터
            accessToken (JWT) : header
        파라미터 예시
            -header 'Authorization: JwtToken'

 - 글 쓰기 API (응답으로 articleId 반환, Points 3 증가)
        url           :    /article
        method        :    POST
        필수 파라미터
            accessToken (JWT) : header
            글 제목 : articleTitle(String)
            글 내용 : articleContents(String)
        파라미터 예시
            -header 'Authorization: JwtToken'
            {
                "articleTitle":"articleTitle",
                "articleContents":"articleContents"
            }
            
 - 글 수정 API (응답으로 articleId 반환)
        url           :    /article
        method        :    PUT
        필수 파라미터
            accessToken (JWT) : header
            글 아이디 : articleId (String)
            글 제목 : articleTitle(String)
            글 내용 : articleContents(String)
        파라미터 예시
            -header 'Authorization: JwtToken'
            {
                "articleId":"articleId",
                "articleTitle":"articleTitle",
                "articleContents":"articleContents"
            } 
 
 - 글 조회 API (응답으로 articleId 와 연결된 commentsId 반환)
        url           :    /article/{articleId}
        method        :    GET
        필수 파라미터
            accessToken (JWT) : header
            글 아이디 : articleId (String)
        파라미터 예시
            -header 'Authorization: JwtToken'
            /article/1
        구현 방법
            : JPA 로 구현
            

 - 글 삭제 API (응답으로 삭제 count 1 반환, 해당 글로 획득한 Points 전부 제거)
        url           :    /article/{articleId}
        method        :    DELETE
        필수 파라미터
            accessToken (JWT) : header
            글 아이디 : articleId (String)
        파라미터 예시
            -header 'Authorization: JwtToken'
            /article/1

 - 댓글 쓰기 API (응답으로 articleId 와 연결된 commentsId 반환, 댓글 작성자 Points 2 증가, 원글 작성자 Points 1 증가)
        url           :    /comments
        method        :    POST
        필수 파라미터
            accessToken (JWT) : header
            글 id : articleId (String)
            댓글 내용 : commentsCotents (String)
        파라미터 예시
            -header 'Authorization: JwtToken'
            {
                "articleId":"articleId",
                "commentsCotents ":"commentsCotents "
            }

 - 댓글 삭제 API (응답으로 articleId 와 연결된 commentsId 반환, 해당 댓글로 획득된 모든 Points 제거)
        url           :    /comments/{commentsId}
        method        :    DELETE
        필수 파라미터
            accessToken (JWT) : header
            댓글 아이디 : commentsId (String)
        파라미터 예시
            -header 'Authorization: JwtToken'
            /article/1 
            
            
         
