package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // 회원 정보 저장하는 Map. Key는 id(Long), Value는 회원 객체(Member)
    private static long sequence = 0L; // 회원이 새로 저장될 때마다 sequence 값이 증가하여 고유 id 부여

    @Override // 인터페이스에서 정의한 메서드 구현
    public Member save(Member member) { // 새 회원 저장
        member.setId(++sequence); // sequence를 증가시켜 고유 id 생성 후, 회원 객체에 할당(setId)
        store.put(member.getId(), member); // Key=Id, Value=member를 store에 저장(put)
        return member;
    }

    @Override
    // Id를 통해 회원 찾고(store.get(id)), Optioanl로 감싸기
    public Optional<Member> findById(Long id) {
        return Optional.of(store.get(id));
    }

    @Override
    // name을 통해 store에서 회원 검색
    public Optional<Member> findByName(String name) {
        return store.values().stream() // store에 저장된 value를 반환하며, 스트림으로 변환
                .filter(member -> member.getName().equals(name)) // member의 value에 있는 name과 일치하는 name 객체만 필터링
                .findAny(); // 조건 맞는 첫 번째 객체를 반환하며, 결과는 Optional로 감쌈
    }

    @Override
    // 저장된 모든 회원 객체 반환(store.values()). ArrayList로 변환. List 형태로 출력
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
