package com.connect.oneboardserver.domain.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Submit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long assignmentId;

    @Column(nullable = false)
    private Long studentId;

    @Column(length = 300)
    private String content;

    @Column(length = 128)
    private String fileUrl;

    @Column
    private int score;

    @Column(length = 300)
    private String feedback;

    @Column(nullable = false)
    private Timestamp createdDt;

    @Column
    private Timestamp updatedDt;


}
