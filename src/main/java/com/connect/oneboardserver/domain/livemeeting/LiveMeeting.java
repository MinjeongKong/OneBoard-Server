package com.connect.oneboardserver.domain.livemeeting;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
