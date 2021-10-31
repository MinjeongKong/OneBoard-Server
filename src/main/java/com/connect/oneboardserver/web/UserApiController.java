package com.connect.oneboardserver.web;

import com.connect.oneboardserver.service.user.UserService;
import com.connect.oneboardserver.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/user")
    public UserResponseDto findUser() {
        return userService.findUser();
    }
}