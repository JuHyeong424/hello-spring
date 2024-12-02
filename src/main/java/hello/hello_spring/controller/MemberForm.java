package hello.hello_spring.controller;

public class MemberForm {
    private String name; // createMemberForm.html에서 작성한 name 들어옴

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
