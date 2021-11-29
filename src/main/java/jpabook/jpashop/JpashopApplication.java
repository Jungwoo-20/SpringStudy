package jpabook.jpashop;

import jpabook.jpashop.study.springboot.StartingEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpashopApplication.class, args);
//        SpringApplication application = new SpringApplication(JpashopApplication.class);
//        application.addListeners(new StartingEvent());
//        application.run(args);
    }

}
