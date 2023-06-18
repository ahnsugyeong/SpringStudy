package com.suddiyo.springboot.config.auth.dto;

import com.suddiyo.springboot.domain.user.Member;
import lombok.Getter;

import java.io.Serializable;

/*
인증된 사용자의 정보를 담는 클래스
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;


    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
