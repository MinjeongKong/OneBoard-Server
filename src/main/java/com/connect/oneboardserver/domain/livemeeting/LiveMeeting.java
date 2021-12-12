package com.connect.oneboardserver.domain.livemeeting;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Entity
public class LiveMeeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String session;

    @Column
    private String startDt;

    @Column
    private String endDt;

    @Builder
    public LiveMeeting(String session, String startDt, String endDt) {
        this.session = session;
        this.startDt = startDt;
        this.endDt = endDt;
    }

    public void startLiveMeeting() {
        this.startDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void closeLiveMeeting() {
        this.endDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
