package tn.esprit.spring.Schedular;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class _4SE1Class {

    @Scheduled(fixedDelay = 3000)
    public void fixedDelayMethod() {
        System.out.println("Hello fixedDelay " + LocalDateTime.now());
    }

    @Scheduled(fixedRate = 3000)
    public void fixedRateMethod() {
        System.out.println("Hello fixedRate " + LocalDateTime.now());
    }
}
