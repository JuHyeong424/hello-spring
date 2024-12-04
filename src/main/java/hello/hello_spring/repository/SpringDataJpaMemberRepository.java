package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// spring data jpa로 공통 인터페이스 만들기
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    // JPQL select m from Member m where m.name = ?
    Optional<Member> findByName(String name); // 이름은 공통 class가 불가해서 작성
}
