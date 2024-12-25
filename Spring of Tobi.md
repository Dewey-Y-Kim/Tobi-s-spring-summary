00.들어가며
=========
스프링이란 무엇인가
--
JAVA 앤터프라이즈 애플리케이션 개발 프레임워크

> 기본틀 = 스프링 컨테이너
> > 설정정보 참고하여   
> > 에플리케이션 구성하는 오브젝트 생성, 관리. 독립동작 가능.   
> > but 주로, 웹 서비스, 서블릿으로 등록하여 사용.   

> 공통 프로그래밍 모델
> IoC(Inversion of Control), 서비스 추상화(PSA, Portable Service Abstractions), AOP(Aspect-Oriented Programming)
>
> > Ioc/DI
> > > 오브젝트 생명주기와 의존관계.
> 
> > 서비스 추상화
> > >구체적 기술과 환경에 종속되지 않도록 추상계층 생성
> 
> > AOP
> > > 부가기능의 모듈화.   
> 기술 API
> > 스프링의 방대한 API.    UI작성, 웹프레젠테이션, 비지니스 서비스, 기반서비스, 도메인, 데이터 액세스 등 주요 기술에서 일관적 구성

스프링의 성공요인
--
> 단순함과 유연성
> > ## 단순함
> > 자바 = 객체지향언어.   단순함 모토.   But 기술이 복잡해짐으로써 특징 소실중.
> > 스프링 - 객체지향의 단순함을 살리는 도구.   POPJ프로그래밍(Plain Old Java Object).
>
> > ## 유연성
> > 확장성과 유연성.
> > 많은 서드파티 프레임워크 지원.

스프링의 학습과 활용의 어려움
--
> 이부분은 작자의 생각이 많이 들어간 듯 하므로 패스.

> 목차만 작성해둠

> 스프링의 핵심가치와 원리에 대한 이해   

> 스프링 기술에 대한 지식과 선택 기준 정립   

> 스프링 적용과 확장

구성과 예제
--
1. 스프링 기본원리 핵심기술
2. 스프링 개별기술의 내용과 응용

01.이해
===

01.오브젝트와 의존관계
--
POJO - 오브젝트의 관계, 사용, 소멸
오브젝트의 설계 - 객체지향 설계, 디자인패턴, 리팩토링, 단위테스트...

1.1. 초난감 DAO
--
DAO (Data Access Object) DB조회 조작기능 전담 Obj.

1.1.1. User
--
```
package User.Domain;

public class User {
    String id;
    String name;
    String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

02.선택
===
03.스피링모듈
===
04.스피링의존라이브러리
==
