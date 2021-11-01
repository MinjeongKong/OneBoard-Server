package com.connect.oneboardserver.web.login;

import com.connect.oneboardserver.service.login.LoginService;
import com.connect.oneboardserver.web.dto.login.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginApiController {

    private final LoginService loginService;

    @PostMapping("/auth/login")
    public Long login(@RequestBody LoginRequestDto loginRequestDto) {
        return loginService.login(loginRequestDto);
    }


    @GetMapping("/auth/logout")

}

