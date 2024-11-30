package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

// ctrl + shift + t 하면 Test class 생성
public class MemberService {

    private final MemberRepository memberRepository;

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
