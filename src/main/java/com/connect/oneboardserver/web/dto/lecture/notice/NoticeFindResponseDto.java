package com.connect.oneboardserver.web.dto.lecture.notice;

import com.connect.oneboardserver.domain.lecture.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeFindResponseDto {

    private Long id;
    private String title;
    private String content;
    private Long lectureId;
    private String exposeDt;
    private String updatedDt;

    @Builder
    public NoticeFindResponseDto(Long id, String title, String content, Long lectureId, String exposeDt, String updatedDt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.lectureId = lectureId;
        this.exposeDt = exposeDt;
        this.updatedDt = updatedDt;
    }

    public static NoticeFindResponseDto toResponseDto(Notice entity) {
        return NoticeFindResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .lectureId(entity.getLecture().getId())
                .exposeDt(entity.getExposeDt())
                .updatedDt(entity.getUpdatedDt())
                .build();
    }
}
