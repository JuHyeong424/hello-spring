package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 사용자 요청 처리 및 응답 반환 값이 뷰 이름으로 해석됨. (hello -> hello.html)
public class HelloController {

    @GetMapping("hello") // HTTP GET request 처리. /hello 경로로 GET 요청 보내면 실행. hello() 메서드 실행
    public String hello(Model model) { // Model은 데이터를 view에 전달하는 객체
        model.addAttribute("data", "hello!"); // view에서 사용할 데이터를 추가. data 속성에 hello! 값 추가. view template에서 ${data} 찾기
        return "hello"; // view resolver가 \resources:templates/\ + hello + .html 찾음.
    }
}
