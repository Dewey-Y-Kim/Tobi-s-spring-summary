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
>
> 단순함과 유연성
>
> > ## 단순함
> >
> > 자바 - 객체지향언어. 단순함 모토.
> >
> > But 기술이 복잡해짐으로써 특징 소실중.
> >
> > 스프링 - 객체지향의 단순함을 살리는 도구.
> >
> > POPJ프로그래밍(Plain Old Java Object).
>
> > ## 유연성
> >
> > 확장성과 유연성.
> > 많은 서드파티 프레임워크 지원.

스프링의 학습과 활용의 어려움
--
>
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
>
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
>
> UserDAO의 관심사항
> >
> > * DB와 연결을 위한 커넥션.  DB종류, 드라이버, 로그인정보, 생성방법......
> > * 사용자정보의 파라미터를 Statement에 바인딩, DB를 통해 실행.
> > * Statement와 Connection obj의 Close. 공유리소스 아끼기.
>
> UserDAO의 생략사항
> >
> > * 예외상황처리 - 수많은 동시사용자. 공유리소스 보존

> 현재의 문제 DB Connection의 분리. add(), get()에 중복되게 들어가있다.

> 추출
>
> ```
> public Connection getConnection(){
>   Class.forName("com.mysql.jdbc.Driver");
>
>   Connection connect = DriverManager.getConnection(
>    "jdbc:mysql://localhost/DB_URL","ID","Password"
>  );
> return connect;
> }
>
> public void add(User user) throws ClassNotFoundException, SQLException{
>  Connection connect = getConnection();
> ...
> 
> public User get(String id) throws ClassNotFoundException, SQLException {
>        Connection connect = getConnection();
> ...
> ```

> 변경사항 검증
> main() 이용 단. ID중복이므로 2회부터는 에러 발생.   이 문제의 해결은 db에서 기존 데이터를 삭제.

중복된 코드를 뽑아내는 것 = 리펙토링(refactoring) : 외부 동작방식 변화 x 내부구조 변경 및 재구성. 이해 용이. 유지보수 용이 유연성 향상.
공통 기능을 담당하는 메소드로 중복된 코드를 뽑아내는 것 = 메소드 추출(extract method)

1.2.3.DB커넥션 만들기의 독립
--

다른 종류의 DB인 경우. 소스 코드 공개 없이 작업.
> 상속에 의한 확장
> 기존 DAO 수정
>
> ```
> public class UserDAO  {
>    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
> ```
>
> DAO를 상속받은 새로운 getConnection()
>
> ```
> public class UserDetailDAO_D extends UserDAO {
>    @Override
>    public Connection getConnection() throws ClassNotFoundException, SQLException {
>        // D사용 getConnection
> ```
>
> ```
> ```
>
> public class UserDetailDAO_N extends UserDAO{
> @Override
> public Connection getConnection() throws ClassNotFoundException, SQLException {
> // N사용 getConnection
> ...
>
> ```

UserDAO - getConnection()이 인터페이스타입이라는 점만 앎
UserDetailDAO_D, UserDetailDAO_N은 Connection 제공 방식과 방법에만 관심.

> #### 디자인 패턴
>
> 자주 반복되는 특정 상황 문제 해결을 위한 재사용 가능 솔루션
>
> 이름만으로 설계의도와 해결책을 함깨 설명가능.
>
> 객체지향적 설계로부터 문제해결으 위해 적용할 수 있는 확장성 추구방법 - 상속, 오브젝트 합성
>
> 패턴의 목적과 의도가 가장 중요

> #### 템플릿 메소드 패턴(Template method pattern)
>
> 상속 이용
> 슈퍼클래스 - 기본적 메소드 흐름. 변하지 않는 기능 작성, 추가, 변경, 확장 할 기능은 추상메소드, 오버라이드 가능한 메소드로 정의.
> 서브클래스 - 변경, 확장기능. 상속받아 작성.
> 훅 메소드 - 슈퍼클래스에서 비워두거나 선택적으로 오버라이드 할 수 있도록 만들어둔 메소드

> #### 팩토리 메소드 패턴(Factory method pattern)
>
> 서브클래스에서 구체적 obj 생성방법을 결정

> #### 상속의 한계
>
> if. 이미 userDAO가 다른 목적을 위해 상속을 받은 상황이라면? - 자바는 다중상속 허용 x, 또한 차후 다른 상속의 적용 힘듦
>
> 상하위 클래스의 긴밀한 관계성. 서브클래스는 슈퍼클래스 기능 직접 사용 가능 = 슈퍼클래스의 변화 -> 모든 서브클래스에 발생. 슈퍼클래스의 변화 제약 필요.
>
> 생성된 DBConnection 기능의 타 DAO적용 불가. 매번 DBConnection 코드 중복 생성.

1.3.DAO으ㅣ 확장
--
>
> 1.3.1 클래스의 분리
> --
>
> 상속을 끊고 독립적인 클래스로 생성
> 재사용성의 증가 목적
>
> ### UserDAO
>
> ```
> public abstract class UserDAO  {
>    private SimpleConnectionMaker simpleConnectionMaker;
>    
>    public UserDAO() {
>        simpleConnectionMaker = new SimpleConnectionMaker();
>    }
> 
>    public void add(User user) throws ClassNotFoundException, SQLException{
>        Connection connect = simpleConnectionMaker.makeNewConnection();
> ...
>
>    public User get(String id) throws ClassNotFoundException, SQLException {
>        Connection connect = simpleConnectionMaker.makeNewConnection();
> ...
> 
> ```

다만 이렇게 한다면 문제 발생 - 확장성이 사라짐.

1.3.2.인터페이스 도입
--

중간고리 생성 = interface.

> ### ConnectionMaker interface
>
> ```
> public interface SimpleConnectionMaker {
>    public Connection makeNewConnection() throws ClassNotFoundException, SQLException ;
> }
> ```
>
> ### ConnectionMaker 구현클래스
>
> ```
> public class UserDetailDAO_D implements SimpleConnectionMaker {
>
>    @Override
>    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
>        // D사용 getConnection
>        Class.forName("com.mysql.jdbc.Driver");
>
>        Connection connect = DriverManager.getConnection(
>                "jdbc:mysql://localhost/DB_URL", "ID", "Password"
>        );
>        return connect;
>    }
> }
> ```
>
> ### UserDAO
>
> ```
> public class UserDAO  {
>    private SimpleConnectionMaker simpleConnectionMaker;
>
>    public UserDAO() {
>        simpleConnectionMaker = new UserDetailDAO_D();
>        // how clear this class name?
>    }
>    public void add(User user) throws ClassNotFoundException, SQLException{
>        Connection connect = simpleConnectionMaker.makeNewConnection();
>        // use interface method name will not change.
> ...
>     public User get(String id) throws ClassNotFoundException, SQLException {
>        Connection connect = simpleConnectionMaker.makeNewConnection();
> ...
> ```

ConnectionMaker = new UserDetailDAO_D();

이 코드가 문제... 초기 어떤 오브젝트를 사용할 지 결정하는 이 코드를 어떻게 처리해야하나..

1.3.3.관계설정 책임의 분리
--

1.3.2의 문제 발생원인 - UserDAO의 관심사에 어떤 ConnectionMaker의 구현체를 사용할 지가 포함되어 있음.
해결 방법 - 사용할 ConnectionMaker의 구현체의 관심사를 Client로 이동.

### ConnectionMaker 생성자

```
public interface ConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException ;
}
...
```

### ClientTest

```
public class Client {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ConnectionMaker connectionMaker = new UserDetailDAO_D();

        UserDAO dao = new UserDAO(connectionMaker);
...
```

Client - 사용할 DB ConnectionMaker 를 결정 DAO를 호출

UserDAO - ConnectionMaker를 받아 get add .. 의 기능 실행

UserDetailDAO - DBConnection의 접속 메소드

1.3.4.원칙과 패턴
--
>
> 개방 폐쇄 원칙
> 클래스나 모듈은 확장에는 열려 있어야 하고, 변경에는 닫혀있어야 한다.
> UserDAO는 DB연결방법, 확장에는 열려있으나, 자신의 핵심기능을 구현한 코드는 변화에 영향을 받지 않는다.

> SOLID
> S(The Single Responsiblility Principle) : 단일 책임 원칙
> O(The Open Closed Principle) : 개방 폐쇄 원칙
> L(The Liskov Subsititution Principle) : 리스코프 치환 원칙
> I(The Interface Segregation Principle) : 인터페이스 분리 원칙
> D(The Dependency Inversion Principle) : 의존관계 역전 원칙

### 높은 응집도와 낮은 결합도
* 높은 응집도 : 하나의 모듈 클래스가 하나의 책임 관심사에만 집중.   
    > 클래스의 변동시 변경 부분이 명확. 다른 클래스의 수정 불요. 테스트 용이. 

* 낮은 결합도
    > 책임과 관심사가 다른 Obj 또는 모듈과는 낮은 결합도 유지.
    > 결합도 - 하나의 Obj가 변경이 일어날 경우, 관계 있는 다른 Obj에게 변화를 요구하는 정도.

* 전략패턴
    > 자신의 기능 context에서 필요에 따라 변경이 필요한 알고리즘을 인터페이스를 통해 통쨰로 외부로 분리, 이를 구현한 구체적 알고리즘 클래스를 필요에 따라 바꿔서 사용할 수 있게 하는 디자인 패턴.   

1.4. 제어의 역전(Inversion of Control)
===
1.4.1. 오브젝트 팩토리
--
ClientTest의 관심사 : DAO테스트<기본>, ConnectionMaker의 구현클래스를 선택  
-> 분리 필요

### 팩토리



02.선택
===========

03.스피링모듈
===

04.스피링의존라이브러리
==
