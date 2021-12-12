package com.connect.oneboardserver.web.dto.analysis;

import com.connect.oneboardserver.domain.understanding.UnderstandPro;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnderstandAnalysisDto {

    private Long understandId;
    private String createdDt;
    private Integer yes;
    private Integer no;

    public UnderstandAnalysisDto(UnderstandPro entity) {
        this.understandId = entity.getId();
        this.createdDt = entity.getCreatedDt();
        this.yes = entity.getYes();
        this.no = entity.getNo();
    }
}
