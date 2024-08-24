package com.shine.task.controller;

import com.shine.task.dto.response.ChildMenuResponse;
import com.shine.task.service.ChildMenuService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/menu/child")
public class ChildMenuController {

    private final ChildMenuService childMenuService;

    // 목록
    @GetMapping("/list")
    public ResponseEntity<List<ChildMenuResponse>> getParentMenuList(@RequestParam Long id) {
        // id 값이 유효한지 확인
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        // 서비스 호출
        return childMenuService.getChildMenus(id);
    }

}
