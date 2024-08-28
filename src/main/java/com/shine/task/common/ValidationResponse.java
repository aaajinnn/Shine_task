package com.shine.task.common;

import com.shine.task.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

@Configuration
@RequiredArgsConstructor
public class ValidationResponse {

    private final MenuRepository menuRepository;

    // name 유효성 체크
    public ResponseEntity<CommonResponse> checkMenuName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(CommonResponse.builder()
                    .response("Menu name cannot be null or empty")
                    .status("Fail")
                    .build());
        }

        if (menuRepository.existsByName(name)) {
            return ResponseEntity.badRequest().body(CommonResponse.builder()
                    .response("Menu name already exists")
                    .status("Fail")
                    .build());
        }
        return null;
    }
}
