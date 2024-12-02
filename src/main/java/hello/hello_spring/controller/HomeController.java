package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // spring MVC Controller
public class HomeController { // 스프링이 관리하는 컨트롤러. client Request(URL)을 처리

    @GetMapping("/") // HTTP GET REQUEST 처리. root route(/)에 대한 GET request 처리
    public String home() {
        return "home"; // home.html 찾음
    }
}
