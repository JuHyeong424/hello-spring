package hello.hello_spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // 공통 관심사 정의 모듈. AOP: 원하는 작동을 분리해서 원하는 메서드에 적용.
@Component // 이 클래스를 spring bean으로 등록하여 aop 기능 활성화
public class TimeTraceAop {

    /*
    @Around: 특정 지점(join point) 전후로 로직 실행
    hello.hello_spring 패키지 및 하위 패키지의 모든 메서드에 AOP를 적용

     */
    @Around("execution(* hello.hello_spring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis(); // 밀리초로 현재 시간 측정. 메서드 실행 시작 시간
        System.out.println("START: " + joinPoint.toString()); // 실행 중인 메서드의 정보를 문자열로 반환.
        try {
            Object result = joinPoint.proceed(); // 실제 대상 메서드 실행
            return result;
        } finally {
            long finish = System.currentTimeMillis(); // 종료 시간
            long timeMs = finish - start; // 메서드 실행 시간
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
