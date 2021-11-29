package jpabook.jpashop.study.springboot;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LogTestController {
    // private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";
        log.error("error {}", name);
        log.warn("warn {}", name);
        log.info("info {}", name);
        log.debug("debug {}", name);
        log.trace("trace {}", name);
        return name;
    }
}
