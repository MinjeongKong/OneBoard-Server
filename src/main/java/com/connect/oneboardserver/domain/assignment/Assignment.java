package com.connect.oneboardserver.domain.assignment;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long lectureId;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 300)
    private String content;

    @Column(length = 128)
    private String fileUrl;

    @Column(nullable = false)
    private LocalDateTime startDt;

    @Column(nullable = false)
    private LocalDateTime endDt;

    @Column
    private LocalDateTime exposeDt;

    @Column(nullable = false)
    private Timestamp createdDt;

    @Column
    private Timestamp updatedDt;

    

}
