
### 지원자 : 김은정

</br>

## 애플리케이션의 실행 방법

배포주소 : ```http://ec2-13-124-55-108.ap-northeast-2.compute.amazonaws.com:8080```

회원가입 ```/users/sign-up```

로그인 ```/login```

게시글 작성 ```/boards``` ```Header 에 Authorization 으로 accessToken 함께 요청```

게시글 수정 ```/boards``` ```Header 에 Authorization 으로 accessToken 함께 요청```

게시글 단일 조회 ```/boards/{board-id}```

게시글 전체 조회 ```/boards?page=1&size=10```

게시글 삭제 ```/boards/1``` ```Header 에 Authorization 으로 accessToken 함께 요청```


</br>

## 데이터베이스 테이블 구조

![image](https://github.com/196code-gray/wanted-pre-onboarding-backend/assets/88307264/67755312-e6eb-453d-8b4e-a5a7cc00ff8e)

</br>

## 구현한 API의 동작을 촬영한 데모 영상 링크

https://youtu.be/Uze_2gNhvWw

</br>

## 구현 방법 및 이유

사용 스택
```
java 11
mysql 8.0.3
spring boot 2.7.14, jpa
spring security, jwt
mockito, junit5
```
간단한 게시판 API 입니다.

게시글 등록, 수정, 삭제시 유저 본인을 인증하기 위한송
ErrorResponder : user 인증 과정에서 발생되는 예외
```

</br>

## API 명세
[API문서](http://localhost:63342/wanted/src/main/resources/static/docs/index.html?_ijt=qa1rtq89l291bbrc1qn0c45ef0&_ij_reload=RELOAD_ON_SAVE#_%EA%B2%8C%EC%8B%9C%EA%B8%80_%EB%93%B1%EB%A1%9D)

## 요구사항

- 과제 1. 사용자 회원가입 엔드포인트
    - 이메일과 비밀번호로 회원가입할 수 있는 엔드포인트를 구현해 주세요. -> 완료
    - 이메일과 비밀번호에 대한 유효성 검사를 구현해 주세요. -> 완료
        - 이메일 조건: @ 포함 -> 완료
        - 비밀번호 조건: 8자 이상 -> 완료
        - 비밀번호는 반드시 암호화하여 저장해 주세요. -> 완료
        - 이메일과 비밀번호의 유효성 검사는 위의 조건만으로 진행해 주세요. 추가적인 유효성 검사 조건은 포함하지 마세요. -> 완료
     
- 과제 2. 사용자 로그인 엔드포인트 
    - 사용자가 올바른 이메일과 비밀번호를 제공하면, 사용자 인증을 거친 후에 JWT(JSON Web Token)를 생성하여 사용자에게 반환하도록 해주세요. -> 완료
    - 과제 1과 마찬가지로 회원가입 엔드포인트에 이메일과 비밀번호의 유효성 검사기능을 구현해주세요. -> 완료
      
- 과제 3. 새로운 게시글을 생성하는 엔드포인트 -> 완료

- 과제 4. 게시글 목록을 조회하는 엔드포인트 -> 완료

- 과제 5. 특정 게시글을 조회하는 엔드포인트 -> 완료
    - 게시글의 ID를 받아 해당 게시글을 조회하는 엔드포인트를 구현해 주세요. -> 완료
    
- 과제 6. 특정 게시글을 수정하는 엔드포인트 -> 완료
    - 게시글의 ID와 수정 내용을 받아 해당 게시글을 수정하는 엔드포인트를 구현해 주세요. -> 완료
    - 게시글을 수정할 수 있는 사용자는 게시글 작성자만이어야 합니다. -> 완료
    
- 과제 7. 특정 게시글을 삭제하는 엔드포인트 -> 완료

