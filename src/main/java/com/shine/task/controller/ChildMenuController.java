package com.shine.task.controller;

import com.shine.task.common.CommonResponse;
import com.shine.task.dto.request.MenuUpdateRequest;
import com.shine.task.dto.response.ChildMenuResponse;
import com.shine.task.service.ChildMenuService;
import com.shine.task.service.ParentMenuService;
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
    private final ParentMenuService parentMenuService;

    // 목록
    @GetMapping("/list")
    public ResponseEntity<List<ChildMenuResponse>> getParentMenuList(@RequestParam Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        return childMenuService.getChildMenus(id);
    }

    // 순서 변경
    @PutMapping("/save-order")
    public ResponseEntity<CommonResponse> saveListOrder(@RequestBody List<MenuUpdateRequest> menu) {
        return parentMenuService.updateListOrder(menu);
    }

    // 등록
    @PutMapping("/save/{parentId}")
    public ResponseEntity<?> createOrUpdateParentMenu(@PathVariable Long parentId, @RequestBody MenuUpdateRequest menuUpdateRequest) {
        return childMenuService.createChildMenu(parentId, menuUpdateRequest);
    }

    // 수정
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateChildMenu(@PathVariable Long id, @RequestBody MenuUpdateRequest menuUpdateRequest) {
        return childMenuService.updateChildMenu(id, menuUpdateRequest);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteChildMenuById(@PathVariable Long id) {
        return childMenuService.deleteChildMenu(id);
    }
}
