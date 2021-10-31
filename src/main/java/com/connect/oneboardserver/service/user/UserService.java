package com.connect.oneboardserver.service.user;

import com.connect.oneboardserver.domain.user.User;
import com.connect.oneboardserver.domain.user.UserRepository;
import com.connect.oneboardserver.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto findUser() {
        List<User> userList = userRepository.findAll();
        User user = userList.get(0);

        return new UserResponseDto(user);
    }
}
