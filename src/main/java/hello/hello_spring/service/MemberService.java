package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// ctrl + shift + t 하면 Test class 생성
//@Service // container에서 Service인지를 지정해서 MemberController와 연결 가능하게 함

@Transactional // data 저장 및 변경. join 메서드 실행 시
public class MemberService {

    private final MemberRepository memberRepository;

    //@Autowired // MemberRepository가 필요하다고 인지시켜 연결해줌
    public MemberService(MemberRepository memberRepository) { // alt + insert
        this.memberRepository = memberRepository;
    }

    /*
        회원가입
         */
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member); // 중복 회원 없으면 repository에 저장
        return member.getId(); // Id 반환
    }

    // ctrl + alt + shift + t -> extract method
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) // member의 name으로 저장서에 있는 정보 찾기
                        .ifPresent(m -> { // name이 이미 존재하면 예외 발생
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }
    /*
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
         */

    /*
    전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /*
    회원 찾기
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
