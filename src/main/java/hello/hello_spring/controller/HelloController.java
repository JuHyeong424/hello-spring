package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 사용자 요청 처리 및 응답 반환 값이 뷰 이름으로 해석됨. (hello -> hello.html)
public class HelloController {

    @GetMapping("hello") // HTTP GET request 처리. /hello 경로로 GET 요청 보내면 실행. hello() 메서드 실행
    public String hello(Model model) { // Model은 데이터를 view에 전달하는 객체
        model.addAttribute("data", "hello!"); // view에서 사용할 데이터를 추가. data 속성에 hello! 값 추가. view template에서 ${data} 찾기
        return "hello"; // view resolver가 \resources:templates/\ + hello + .html 찾음.
    }

    @GetMapping("hello-mvc") // url이 hello-mvc인 GET 요청이 오면 메서드 호출
    // @RequestParam("name"): query parameter name의 값을 method parameter name으로 바인딩. http://localhost:8080/hello-mvc?name=John이라면 name 변수는 "John"
    // Model model: MVC에서 제공하는 객체. controller에서 data를 view로 전달
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name); // 모델에 데이터 추가. name이라는 key에 name value 추가. view에서 사용
        return "hello-template"; // hello-template.html이라는 view가 렌더링.
    }

    @GetMapping("hello-string") // url이 hello-string인 GET 요청 시, 메서드 호출
    /*
    @ResponseBody는 HTTP의 Body에 문자 직접 반환.
    viewResolver 대신 HttpMessageConverter 동작.
    문자처리: StringHttpMessageConverter. 객체처리: MappingJackson2HttpMessageConverter
     */
    @ResponseBody
    public String helloString(@RequestParam("name") String name) { // query parameter name 값을 method name으로 바인딩
        return "hello " + name; // hello-string?name=string이면 hello string이 표시
    }

    @GetMapping("hello-api") // url이 hello-api에 GET 요청 시 메서드 호출
    /*
    @ResponseBody는 HTTP의 Body에 문자 직접 반환.
    viewResolver 대신 HttpMessageConverter 동작.
    객체처리: MappingJackson2HttpMessageConverter
     */
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) { // querty parameter name을 메서드 parameter name에 매핑. hello-api?name=api -> name = "api"
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // JSON으로 반환. { "name": "api" }
    }

    static class Hello { // static: 외부 인스턴스와 독립적으로 사용
        private String name; // 외부 접근 막으면서 캡슐화

        public void setName(String name) { // 외부에서 name 변수 저징
            this.name=name; // 메서드 name을 class name에 할당
        }

        public String getName() { // name 변수 읽어서
            return name; // 클래스 내부 name 반환
        }
    }


}

