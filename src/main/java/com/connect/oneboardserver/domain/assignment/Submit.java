package com.connect.oneboardserver.domain.assignment;

import com.connect.oneboardserver.domain.BaseTimeEntity;
import com.connect.oneboardserver.domain.login.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class Submit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Assignment assignment;

    @ManyToOne
    private Member student;

    @Column(length = 300)
    private String content;

    @Column(length = 128)
    private String fileUrl;

    @Column(length = 128)
    private String loadUrl;

    @Column
    private Float score;

    @Column(length = 300)
    private String feedback;

    @Builder
    public Submit(String content, String fileUrl) {
        this.content = content;
        this.fileUrl = fileUrl;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public void setStudent(Member student) {
        this.student = student;
    }

    public void check(Float score, String feedback) {
        this.score = score;
        this.feedback = feedback;
    }

    public void update(String content, String fileUrl) {
        this.content = content;
        this.fileUrl = fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setLoadUrl(String loadUrl) {
        this.loadUrl = loadUrl;
    }
}
