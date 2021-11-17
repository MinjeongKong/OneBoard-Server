package com.connect.oneboardserver.domain.grade;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class GradeRatio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer aRatio;

    @Column(nullable = false)
    private Integer bRatio;


    @Builder
    public GradeRatio(Integer aRatio, Integer bRatio) {
        this.aRatio = aRatio;
        this.bRatio = bRatio;
    }

    public void update(GradeRatio entity) {
        this.aRatio = entity.getARatio();
        this.bRatio = entity.getBRatio();
    }


    public boolean isValid() {
        if (this.getARatio() + this.getBRatio() <= 100) {
            return true;
        } else {
            return false;
        }
    }
}
