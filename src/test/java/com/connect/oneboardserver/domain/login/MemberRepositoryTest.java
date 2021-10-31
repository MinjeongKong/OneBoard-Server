package com.connect.oneboardserver.domain.login;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        String student_num = "201823787";
        String name = "Minjeong Kong";
        String password = "0000";
        String email = "kong1301@ajou.ac.kr";
        int user_type = 0;
        String university = "Ajou";
        String major = "Software";
        String lecture_id = "F034";

        memberRepository.save(Member.builder()
                .student_num(student_num)
                .name(name)
                .password(password)
                .email(email)
                .user_type(user_type)
                .university(university)
                .major(major)
                .lecture_id(lecture_id)
                .build());

        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        Member a = memberList.get(0);
        assertThat(a.getStudent_num()).isEqualTo(student_num);
        assertThat(a.getEmail()).isEqualTo(email);
        assertThat(a.getUser_type()).isEqualTo(user_type);
    }
}
