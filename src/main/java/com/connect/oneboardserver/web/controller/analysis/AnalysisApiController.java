package com.connect.oneboardserver.web.controller.analysis;

import com.connect.oneboardserver.service.analysis.AnalysisService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class AnalysisApiController {

    private final AnalysisService analysisService;

    @GetMapping("/lecture/{lectureId}/lesson/{lessonId}/analysis")
    public ResponseDto findAnalysis(@PathVariable Long lectureId, @PathVariable Long lessonId) {
        return analysisService.findAnalysis(lectureId, lessonId);
    }
}
