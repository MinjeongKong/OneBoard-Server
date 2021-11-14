package com.connect.oneboardserver.service.assignment;

import com.connect.oneboardserver.domain.assignment.Assignment;
import com.connect.oneboardserver.domain.assignment.AssignmentRepository;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.assignment.AssignmentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AssignmentFileService {

    @Qualifier("FileStorageService")
    private final StorageService storageService;

    private final AssignmentRepository assignmentRepository;

    @Transactional
    public int uploadAssignment(Long assignmentId, MultipartFile file) {
        if (file.isEmpty()) {
            return 1;
        }

        Assignment assignment = null;
        String uploadedFile = null;

        assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 과제가 없습니다. id="+assignmentId));

        try {
            //과제안내 파일이 있으면 파일 삭제
            if (assignment.getFileUrl() != null) {
                if (storageService.delete(assignment.getFileUrl())) {
                    assignment.setFileUrl(null);
                }
            }

            String path = "/assignment_" + assignmentId;
            uploadedFile = storageService.store(path, file);
        } catch (Exception e) {
            return 1;
        }

        assignment.setFileUrl(uploadedFile);

        return 0;
    }

}
