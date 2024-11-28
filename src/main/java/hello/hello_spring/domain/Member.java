package hello.hello_spring.domain;

// Member 클래스를 통해 회원 도메인을 만들고 repository에 저장한다.
// Getter는 반환값이 필요. Setter는 반환값이 불필요하므로 void 사용.

public class Member {
    private Long id; // 회원 고유 식별자
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
