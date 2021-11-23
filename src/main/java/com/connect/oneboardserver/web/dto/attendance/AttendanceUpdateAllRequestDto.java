package com.connect.oneboardserver.web.dto.attendance;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AttendanceUpdateAllRequestDto {

    private List<AttendanceUpdateRequestDto> updateDataList;

    public AttendanceUpdateAllRequestDto(List<AttendanceUpdateRequestDto> updateDataList) {
        this.updateDataList = updateDataList;
    }
}
