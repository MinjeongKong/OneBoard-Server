package com.connect.oneboardserver.web.login;

import com.connect.oneboardserver.config.security.JwtTokenProvider;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.login.MemberRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.login.LoginRequestDto;
import com.connect.oneboardserver.web.dto.login.MemberJoinDto;
import com.connect.oneboardserver.web.dto.login.MemberResponseDto;
import com.connect.oneboardserver.web.dto.login.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final UserDetailsService userDetailsService;

    // 회원가입
    @PostMapping("/join")
    public Long join(@RequestBody MemberJoinDto user) {
        return memberRepository.save(Member.builder()
                .studentNumber(user.getStudentNumber())
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
//                .password(user.getPassword())
                .email(user.getEmail())
                .userType(user.getUserType())
                .university(user.getUniversity())
                .major(user.getMajor())
                .lecture_id(user.getLecture_id())
                .roles(Collections.singletonList("ROLE_" + user.getUserType()))
                .build()).getId();

    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseDto login(@Valid @RequestBody LoginRequestDto user) {
        Member member = memberRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        TokenDto tokenDto = new TokenDto(member, jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
        return new ResponseDto("SUCCESS", tokenDto);
    }

    @GetMapping("/member")
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    @GetMapping("/student")
    public String student() {
        return "hello student!";
    }

    @GetMapping("/user")
    public ResponseDto userInfo(ServletRequest request) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Member member = (Member) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return new ResponseDto("SUCCESS", memberResponseDto);
    }

    @GetMapping("/auth/check")
    public ResponseDto authcheck(ServletRequest request) {
        return new ResponseDto("SUCCESS");
    }
}

