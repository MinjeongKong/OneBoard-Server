package com.connect.oneboardserver.domain.login;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    public void cleanup() {
        memberRepository.deleteAll();
    }

    @Test
    public void save_and_findAll() {
        //given
        String studentNumber = "201823787";
        String name = "Minjeong Kong";
        String password = "0000";
        String email = "kong1301@ajou.ac.kr";
        String user_type = "S";
        String university = "Ajou";
        String major = "Software";

        memberRepository.save(Member.builder()
                .studentNumber(studentNumber)
                .name(name)
                .password(password)
                .email(email)
                .userType(user_type)
                .university(university)
                .major(major)
                .roles(Collections.singletonList(user_type))
                .build());

        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        Member a = memberList.get(0);
        assertThat(a.getStudentNumber()).isEqualTo(studentNumber);
        assertThat(a.getEmail()).isEqualTo(email);
    }
}
