package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.form.MemberForm;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        return "members/join-form";
    }

    //Vaild를 사용하면 BindingResult(에러가 발생하면 여기에 담아서 이유를 알려줌)를 반드시 사용
    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<String, String>();
            bindingResult.getFieldErrors().stream().forEach(fieldError -> {
                String filedName = fieldError.getField();
                String errorMsg = fieldError.getDefaultMessage();
                errorMap.put(filedName, errorMsg);
            });
            model.addAttribute("error", errorMap);
            model.addAttribute("memberForm", memberForm);
            return "/members/join-form";
        }
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/member-list";
    }
}
