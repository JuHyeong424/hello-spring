package hello.hello_spring.repository;

// Member 클래스가 정의된 경로 가져옴
import hello.hello_spring.domain.Member;

import java.util.List;

// null을 안전하게 처리하는 클래스. null인 객체를 감싸는 container
import java.util.Optional;

/*
회원 정보를 저장하고 검색하는 작업
인터페이스는 저장소에 대한 표준화된 메서드 정의
이를 implements하는 class는 메서드 구체화
*/

public interface MemberRepository {

    // 회원 정보 저장
    Member save(Member member);

    // id를 기준으로 정보 조회. type은 Member로. 회원정보가 없으면 Optional.empty() 반환
    Optional<Member> findById(Long id);

    // name을 기준으로 정보 조회.
    Optional<Member> findByName(String name);

    // 모든 회원 정보 반환
    List<Member> findAll();

}
