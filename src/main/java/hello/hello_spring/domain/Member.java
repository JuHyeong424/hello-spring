package hello.hello_spring.domain;

// Member 클래스를 통해 회원 도메인을 만들고 repository에 저장한다.
// Getter는 반환값이 필요. Setter는 반환값이 불필요하므로 void 사용.

import jakarta.persistence.*;

@Entity // JPA가 관리하는 entity
public class Member {

    @Id // 해당 필드가 primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key 값이 자동 생성. db에서 auto increment 기능 사용
    private Long id; // 회원 고유 식별자

    //@Column(name = "username") // db column name을 username으로
    private String name; // 회원 이름

    public Long getId() { // id를 반환. long으로 반환
        return id;
    }

    public void setId(Long id) { // id 지정하므로 반환값(return) 불필요. void 이용.
        this.id = id;
    }

    public String getName() { // name 반환. String으로 반환
        return name;
    }

    public void setName(String name) { // name 설정하므로 반환값(return) 불필요. void 이용
        this.name = name;
    }
}
