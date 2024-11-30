package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller // Component Scan으로 자동 등록 @service @repository @Autowired
public class MemberController {

    private final MemberService memberService;

    @Autowired // MemberService가 필요하다고 인식하게 해주어 연결시켜줌
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
