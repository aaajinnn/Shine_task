package com.shine.task.controller;

import com.shine.task.common.CommonResponse;
import com.shine.task.dto.request.MenuUpdateRequest;
import com.shine.task.dto.result.MenuResult;
import com.shine.task.dto.response.ParentMenuResponse;
import com.shine.task.entity.Menu;
import com.shine.task.service.ParentMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/menu/parent")
public class ParentMenuController {

    private final ParentMenuService parentMenuService;

    //테스트(전체메뉴 목록)
    @GetMapping
    public ResponseEntity<List<MenuResult>> getMenuList(){
        final List<MenuResult> menus = parentMenuService.getMenus();
        return ResponseEntity.ok(menus);
    }

    // 목록
    @GetMapping("/list")
    public ResponseEntity<List<ParentMenuResponse>> getParentMenuList(){
        return parentMenuService.getParentMenus();
    }

    // 순서 변경
    @PutMapping("/save-order")
    public ResponseEntity<CommonResponse> saveListOrder(@RequestBody List<MenuUpdateRequest> menu) {
        return parentMenuService.updateListOrder(menu);
    }

    // 등록
    @PutMapping("/save")
    public ResponseEntity<?> createOrUpdateParentMenu(@RequestBody MenuUpdateRequest menuUpdateRequest) {
        return parentMenuService.createParentMenu(menuUpdateRequest);
    }

    // 수정
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateParentMenu(@PathVariable Long id, @RequestBody MenuUpdateRequest menuUpdateRequest) {
        return parentMenuService.updateParentMenu(id, menuUpdateRequest);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteParentMenuById(@PathVariable Long id) {
        return parentMenuService.deleteParentMenu(id);
    }
}
