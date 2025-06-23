package tn.esprit.spring.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class FoyerAspect4SE1 {

    @Before("execution(* tn.esprit.spring.services..*.*(..))")
    public void hello(JoinPoint jp){ // La méthode : Advice
        log.info("Hello from "+jp.getSignature().getName());
    }

    @After("execution(* tn.esprit.spring.services..*.*(..))")
    public void bye(JoinPoint jp){ // La méthode : Advice
        log.info("Out of method "+jp.getSignature().getName());
    }


}
