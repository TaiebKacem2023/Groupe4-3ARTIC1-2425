package tn.esprit.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class FoyerAspect4SIM2 /* La classe: Aspect */  {


    @After("execution(* tn.esprit.spring.services..*.*(..))")
    public void afterAdvice(JoinPoint jp){
        log.info("ranni 5rajt mil méthode "+jp.getSignature().getName());
    }

    // @AfterReturning()
    // @AfterThrowing()

    @Before("execution(* tn.esprit.spring.services..*.*(..))")
    public void beforeAdvice(JoinPoint jp){
        log.info("ranni d5alt lil méthode "+jp.getSignature().getName());
    }

    @Before("execution(* tn.esprit.spring.services..*.ajouter*(..))")
    public void beforeAdvice2(JoinPoint jp){
        log.info("Ranni méthode ajouter");
    }


}
