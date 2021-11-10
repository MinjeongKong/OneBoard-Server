package com.connect.oneboardserver.web;

import com.connect.oneboardserver.service.user.UserService;
import com.connect.oneboardserver.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "localhost:3000")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/user2")
    public UserResponseDto findUser() {
        return userService.findUser();
    }
}