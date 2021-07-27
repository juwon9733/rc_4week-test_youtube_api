package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
// @Getter : Class 내 모든 필드의 Getter method를 자동 생성한다.
@Setter
// @Setter : Class 내 모든 필드의 Setter method를 자동 생성한다.
@AllArgsConstructor
// @AllArgsConstructor : 모든 필드 값을 파라미터로 받는 생성자를 추가한다.
public class User {
    private int Idx;
    private String imageUrl;
    private String name;
    private String birth;
    private String sex;
    private String passwd;
    private String email;
    private String phoneNumber;
}
