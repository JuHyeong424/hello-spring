package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링 컨테이너와 테스트 함께 실행
@Transactional // Test가 끝나면 rollback함. DB에 있는 데이터 삭제. clear 메서드 불필요
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService; // Test에서는 필드기반으로 작성 가능
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception { // Test메서드명은 한글로 적어도 상관없음
        // given
        Member member = new Member();
        member.setName("spring3");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get(); // Optional이므로 get()으로 직접 접근
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
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
}