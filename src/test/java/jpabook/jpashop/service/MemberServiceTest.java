package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test //test에서 db에 반영되면 서로 의존이 될 수도 있기 때문에 commit 이 되어도 rollback이 된다.
    // @Rollback(value = false) //rollback 안함
    public void 회원가입() {
        Member member = new Member();
        member.setName("kim");
        Long saveId = memberService.join(member);
        Assertions.assertThat(member).isEqualTo(memberService.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("kim");
        member2.setName("kim");

        memberService.join(member1);
        //Junit
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                memberService.join(member2));
//        String message = exception.getMessage();
//        System.out.println(message);

        //AssertJ
        assertThatThrownBy(
                () -> {
                    memberService.join(member2);
                }
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("이미 존재하는 회원입니다.");
    }
}