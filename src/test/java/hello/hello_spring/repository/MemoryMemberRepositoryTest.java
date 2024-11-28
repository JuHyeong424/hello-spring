package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

// MemoryMemberRepository 클래스 동작 검증 테스트
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository(); // 테스트에 사용할 MemoryMemberRepository 인스턴스 생성

    @AfterEach // 각 Test 메서드가 실행된 후 호출
    public void afterEach() {
        repository.clearStore(); // 저장소를 초기화. 테스트의 독립 실행 보장
    }

    @Test // 테스트 메서드 실행
    public void save() {
        Member member = new Member(); // Member 객체 생성
        member.setName("spring"); // member의 name을 spring1로 저장

        repository.save(member); // member 정보를 repository에 저장

        // 저장된 회원을 member(spring)의 Id로 조회. Optional이므로 get()을 통해 직접 접근
        Member result = repository.findById(member.getId()).get();
        // 조회된 결과(result)와 저장된 객체(member)가 동일한지 검증
        // Assertions.assertEquals(member, result);
        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
