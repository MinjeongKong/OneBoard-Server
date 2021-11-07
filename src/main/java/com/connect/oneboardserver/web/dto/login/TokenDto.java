package com.connect.oneboardserver.web.dto.login;

import com.connect.oneboardserver.domain.login.Member;
import lombok.*;

@Getter
public class TokenDto {

    private String email;
    private String token;

    public TokenDto(Member entity, String token) {
        this.email = entity.getEmail();
        this.token = token;
    }
}
