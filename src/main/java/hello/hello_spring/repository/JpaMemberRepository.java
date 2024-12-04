package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    /*
    EntityManager는 entity를 db와 연동, 관리
    데이터베이스와 상호작용하며, 엔티티 객체의 생명 주기 관리.
    CRUD 작업과 트랜잭션 처리
     */
    private final EntityManager em;

    // DI(Dependency Injection)을 통해 JPA의 EntityMager 사용
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }


    // new Member Entity를 db에 저장
    @Override
    public Member save(Member member) {
        em.persist(member); // entity를 영속성 context에 추가하고, 트랜잭션이 commit되면 db에 저장
        return member;
    }

    // primary key를 기준으로 회원 조회
    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // Member class에 있는 id를 기준으로 Member entity 조회
        return Optional.ofNullable(member); // 결과가 없으면 null을 Optional로 감싸 처리
    }

    @Override
    public Optional<Member> findByName(String name) {
        // JPQL을 사용해 query 작성
        // Member entity(m)의 모든 데이터를 조회. db table이 아닌 entity class를 기준으로 작성.
        // Member entity 중 name 속성이 지정 값(:name)과 일치하는 데이터 조회
        // Member.class를 명시해 쿼리 결과를 Member entity로 매핑
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                // :name은 쿼리에서 사용되는 named placeholder(like 변수)로, 실행 시점에 값을 전달받아 사용
                .setParameter("name", name) // name parameter(:name)에 실제 값 할당
                .getResultList();

        return result.stream().findAny(); // stream으로 변환한 후, 첫 번째 요소를 하나 반환
    }

    @Override
    public List<Member> findAll() {
        // ctrl alt shift t(inline) 하면 단축
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList(); // 결과를 리스트로 반환
        return result;
    }
}
