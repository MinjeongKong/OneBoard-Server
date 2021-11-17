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
    private Integer aratio;

    @Column(nullable = false)
    private Integer bratio;


    @Builder
    public GradeRatio(Integer aratio, Integer bratio) {
        this.aratio = aratio;
        this.bratio = bratio;
    }

    public void update(GradeRatio entity) {
        this.aratio = entity.getAratio();
        this.bratio = entity.getBratio();
    }


    public boolean isValid() {
        if (this.getAratio() + this.getBratio() <= 100) {
            return true;
        } else {
            return false;
        }
    }
}
