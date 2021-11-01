package com.connect.oneboardserver.domain.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

//    @AfterEach
//    public void cleanUp() {
//        userRepository.deleteAll();
//    }

    @Test
    @DisplayName("학생 저장 및 불러오기")
    public void saveUser() {
        // given
        String studentNumber = "201521004";
        String email = "test@gmail.com";
        String password = "12341234";
        String name = "홍길동";
        String userType = "S";
        String university = "아주대학교";
        String major = "소프트웨어학과";

        userRepository.save(User.builder()
                .studentNumber(studentNumber)
                .email(email)
                .password(password)
                .name(name)
                .userType(userType)
                .university(university)
                .major(major)
                .build());

        // when
        List<User> userList = userRepository.findAll();

        // then
        User user = userList.get(0);
        assertThat(user.getStudentNumber()).isEqualTo(studentNumber);
        assertThat(user.getName()).isEqualTo(name);
    }
}