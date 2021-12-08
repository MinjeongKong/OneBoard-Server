package com.connect.oneboardserver.web.dto.understanding;

import com.connect.oneboardserver.domain.understanding.UnderstandPro;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResultResponseDto {

    private Long lessonId;
    private String lessonTitle;
    private Long understandId;
    private String createdDt;
    private Integer yes;
    private Integer no;

    private List<UnderstandStuFindResponseDto> understandOList;
    private List<UnderstandStuFindResponseDto> understandXList;

    public ResultResponseDto(UnderstandPro entity, Integer yes, Integer no, List<UnderstandStuFindResponseDto> understandOList, List<UnderstandStuFindResponseDto> understandXList) {
        this.lessonId = entity.getLesson().getId();
        this.lessonTitle = entity.getLesson().getTitle();
        this.understandId = entity.getId();
        this.createdDt = entity.getCreatedDt();
        this.yes = yes;
        this.no = no;
        this.understandOList = understandOList;
        this.understandXList = understandXList;
    }
}
