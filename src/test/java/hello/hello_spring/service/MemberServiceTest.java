package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach // 각 테스트 메서드가 실행되기 전 호출
    public void beforeEach() { // 테스트마다 새로운 객체 생성. MemoryMemberRepository에서 생성한 객체를 MemberService에서도 그대로 사용하도록 함
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach // 메서드가 끝나면 repository 비우기
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() { // Test메서드명은 한글로 적어도 상관없음
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get(); // Optional이므로 get()으로 직접 접근
        org.assertj.core.api.Assertions.assertThat(member.getName()).isEqualTo(findMember.getName()); // member의 name과 repository의 name이 같은지 비교
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member(); // shift + F6
        member2.setName("spring");

        //when
        memberService.join(member1); // member1 객체를 memberService에 join
        // member2를 service에 join할 때 IllegalStateException이 발생하는지 테스트(assertThrows) 함.
        // 기대하는 예외타입: IllegalStateException. 예외 발생 코드: () -> memberService.join(member2)
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // e.getMessage(): IllegalStateException 객체의 에러 메서지. 이 에러 메세지가 아래랑 동일한지 비교
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*
        try {
            memberService.join(member2);
            fail("예외가 발생해야 합니다.");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
         */


        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }

    @Test
    void testJoin() {
    }

    @Test
    void testFindMembers() {
    }

    @Test
    void testFindOne() {
    }
}