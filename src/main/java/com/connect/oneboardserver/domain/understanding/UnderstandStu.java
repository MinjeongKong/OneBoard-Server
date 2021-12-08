package com.connect.oneboardserver.domain.understanding;

import com.connect.oneboardserver.domain.login.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UnderstandStu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UnderstandPro understandPro;

    @ManyToOne
    private Member student;

    @Column(nullable = false)
    private Integer response; // 0:X, 1:O

    @Builder
    public UnderstandStu(UnderstandPro understandPro, Member student, Integer response) {
        this.understandPro = understandPro;
        this.student = student;
        this.response = response;
    }

    public void setUnderstandPro(UnderstandPro understandPro) {
        this.understandPro = understandPro;
    }

    public void setStudent(Member student) {
        this.student = student;
    }
}
