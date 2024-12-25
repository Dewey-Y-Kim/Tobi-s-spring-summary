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
> > 자바 - 객체지향언어. 단순함 모토.
> > 
> > But 기술이 복잡해짐으로써 특징 소실중.
> > 
> > 스프링 - 객체지향의 단순함을 살리는 도구.
> > 
> > POPJ프로그래밍(Plain Old Java Object).
>
> > ## 유연성
> > 확장성과 유연성.
> > 많은 서드파티 프레임워크 지원.

스프링의 학습과 활용의 어려움
--
> 이부분은 작자의 생각이 많이 들어간 듯 하므로 패스.
> 
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
|필드명|타임|설정|
|--|--|--|
|Id|varchar 10| PK|
|Name|varchar 20| Not Null|
|Password|varchar 20 |Not Null|

#### DDL 예시
```
create table users(
	id varchar(10) primary key,
	name varchar(20) not null,
	password varchar(20) not null
)
``` 
1.1.2. UserDAO
--
일단은 get add만
```
package User.DAO;


import User.Domain.User;

import java.sql.*;

public class UserDAO  {
    public void add(User user) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection connect = DriverManager.getConnection(
                "jdbc:mysql://localhost/DB_URL","ID","Password"
        );
        PreparedStatement ps = connect.prepareStatement(
                "insert into users(id, name, password) value(?,?,?)"
        );
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        
        ps.executeQuery();
        connect.close();
    }
    
    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection connect = DriverManager.getConnection(
                "jdbc:mysql://localhost/DB_URL", "ID", "Password"
        );

        PreparedStatement ps = connect.prepareStatement(
                "select id, name, password from users where id = ?"
        );
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        
        rs.close();
        ps.close();
        connect.close();
        
        return user;
    }
}

```
1.1.3.DAO테스트코드
--
main()을 이용. 
```
import User.DAO.UserDAO;
import User.Domain.User;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDAO dao = new UserDAO();

        User user = new User();
        user.setId("001");
        user.setName("dewey");
        user.setPassword("testPassword");
        
        dao.add(user);
        
        System.out.println(user.getId()+" is added");
        
        User user2 = dao.get(user.getId());
        
        System.out.println(user2.getName() + " is loaded");
        
        System.out.println(user2.getId() + "`s password is " + user2.getPassword());
        
    }
}
```
Db 드라이버 넣기

1.1.4. 정리
--
> 이 코드의 문제점
>
```
Class.forName("com.mysql.jdbc.Driver");
Connection connect = DriverManager.getConnection(
	"jdbc:mysql://localhost/DB_URL","ID","Password");
```
> 중복된 코드
> 동시사용자의 사용시 예외처리

> 아는만큼 보이니 일단 현재 보이는 거 위주로 정리함

1.2.DAO의 분리
--
1.2.1.관심사의 분리
--
코드는 폐기전까지 변화. 그 주기는 몇 시간~폐기.
고로 미래를 대비한 설계 필요.
그러기위한 수단중에 하나. 관심사의 분리
코드 한줄 수정이 수백 수만줄 수정보다 편하다.

1.2.2.커넥션 생성의 추출
--
> UserDAO의 관심사항
> > * DB와 연결을 위한 커넥션.		DB종류, 드라이버, 로그인정보, 생성방법......
> > * 사용자정보의 파라미터를 Statement에 바인딩, DB를 통해 실행.
> > * Statement와 Connection obj의 Close. 공유리소스 아끼기.
>
> UserDAO의 생략사항
> > * 예외상황처리 - 수많은 동시사용자. 공유리소스 보존

> 현재의 문제 DB Connection의 분리. add(), get()에 중복되게 들어가있다.

> 추출
>
> ```
> public Connection getConnection(){
> 	Class.forName("com.mysql.jdbc.Driver");
>
>   Connection connect = DriverManager.getConnection(
>   	"jdbc:mysql://localhost/DB_URL","ID","Password"
>		);
>		return connect;
> }
>
>	public void add(User user) throws ClassNotFoundException, SQLException{
> 	Connection connect = getConnection();
> ...
> 
>	public User get(String id) throws ClassNotFoundException, SQLException {
>        Connection connect = getConnection();
> ...
> ```

02.선택
===
03.스피링모듈
===
04.스피링의존라이브러리
==
