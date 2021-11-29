package jpabook.jpashop.study.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
    private MemberRepository memberRepository = new MemberRepository();

    @RequestMapping("/new")
    public ModelAndView newMember() {
        return new ModelAndView("new-form");
    }
//    @RequestMapping("/new")
//    public String newMember() {
//        return "new-form";
//    }

    // @RequestMapping(value = "/add", method = RequestMethod.POST) //method를 추가하여 특정 요청만 수용 가능
//    @PostMapping("/add") // 이게 훨씬 가독성 좋음
//    public ModelAndView addMember(HttpServletRequest req, HttpServletResponse resp) {
//        String username = req.getParameter("name");
//        int age = Integer.parseInt(req.getParameter("age"));
//        Member member = new Member(username, age);
//        memberRepository.save(member);
//        ModelAndView mv = new ModelAndView("add-result");
//        return mv;
//    }

//    @PostMapping("/add") //@RequestParam의 value와 함수 내 값 이름이 동일하면 생략가능
//    public String addMember(@RequestParam(value = "name",required = false) String username, int age, Model model) {
//        Member member = new Member(username, age);
//        memberRepository.save(member);
//        model.addAttribute("member", member);
//        return "add-result";
//    }

    @PostMapping("/add") //@ModelAttribute 를 활용하면 가장 간단함(이름 맞추는거 중요함!)
    public String addMember(@ModelAttribute Member member, Model model) {
        memberRepository.save(member);
        model.addAttribute("member", member);
        return "add-result";
    }

    @RequestMapping("/all")
    public ModelAndView allMember() {
        List<Member> members = memberRepository.findAll();
        ModelAndView mv = new ModelAndView("member-list");
        mv.addObject("members", members);
        return mv;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "<html><body><h1>Hello, ResponseBody!</h1></body></html>";
    }

    @GetMapping("/api/{id}/{age}")
    public String getMemberById(@PathVariable("id") Long id, @PathVariable("age") int age) {
        return "name : " + memberRepository.findById(id).getName() + ", age : " + age;
    }

    @PostMapping("/requestBody")
    public String requestBodyHandler(@RequestBody Member member) {
        log.info("member={}", member);
        return "ok";

    }

    @PostMapping("/requestBody2")
    public HttpEntity<String> requestBodyHandler2(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        log.info("member={}", body);
        return new HttpEntity<String>("ok");
    }
}
