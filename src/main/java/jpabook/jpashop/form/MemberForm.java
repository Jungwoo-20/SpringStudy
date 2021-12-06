package jpabook.jpashop.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

// member 도메인에 코드를 많이 쓰면 지저분해 보여서 따로 처리
@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;

}
