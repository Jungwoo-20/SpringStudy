package jpabook.jpashop.study.controller;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
