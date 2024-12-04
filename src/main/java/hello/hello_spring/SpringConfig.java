package hello.hello_spring;

import hello.hello_spring.repository.*;
import hello.hello_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/*
자바 코드로 직접 spring 빈 등록
 */
@Configuration // 스프링 설정 클래스. 스프링 컨테이너에 등록되며, 내부에 정의된 @Bean을 통해 spring 빈 생성 관리
public class SpringConfig {

    /*
    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
     */

    EntityManager em;
    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean // 해당 메서드가 반환하는 객체를 스프링 빈으로 등록. 스프링 컨테이너가 객체를 관리하며 다른 곳에서 injection 가능
    public MemberService memberService() { // 빈 이름 memberService
        return new MemberService(memberRepository()); // Constructor Injection을 사용해 MemberRepository를 의존성으로 전달
    }

    @Bean
    public MemberRepository memberRepository() {
        // return new MemoryMemberRepository();
        // return new JdbcMemberRepository(dataSource);
        // return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}
