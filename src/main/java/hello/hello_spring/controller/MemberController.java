package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller // Component Scan으로 자동 등록 @service @repository @Autowired
public class MemberController {

    private final MemberService memberService;

    @Autowired // MemberService가 필요하다고 인식하게 해주어 연결시켜줌
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/new") // data 조회
    public String createForm() {
        return "members/createMemberForm"; // open
    }

    @PostMapping(value = "/members/new") // data를 form에 넣어서 전달. 회원가입 버튼
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName()); // MemberForm class에서 name 꺼냄

        memberService.join(member); // member가 저장소에 등록

        return "redirect:/"; // Home으로 이동
    }

    @GetMapping(value = "/members") // 회원 목록 버튼
    public String list(Model model) { // 요청 처리, 데이터를 부로 전달, 렌더링할 뷰 반환. Model model(뷰에 데이터 전달 객체)
        List<Member> members = memberService.findMembers(); // memberService에서 모든 회원 정보 가져와서 List에 반환
        model.addAttribute("members", members); // model 객체를 이용해 뷰에 데이터 전달. "members"라는 이름으로 members 리스트를 뷰에 추가.
        return "members/memberList"; // 뷰 이름. members/memberList.html
    }

}
