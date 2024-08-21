package com.shine.task.controller;

import com.shine.task.common.CommonResponse;
import com.shine.task.dto.request.MenuUpdateRequest;
import com.shine.task.dto.result.MenuResult;
import com.shine.task.dto.response.ParentMenuResponse;
import com.shine.task.service.ParentMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/menu/parent")
public class ParentMenuController {

    private final ParentMenuService parentMenuService;

    //테스트(전체메뉴 목록)
    @GetMapping("/test")
    public ResponseEntity<List<MenuResult>> getMenuList(){
        final List<MenuResult> menus = parentMenuService.getMenus();
        return ResponseEntity.ok(menus);
    }

    // 목록
    @GetMapping("/list")
    public ResponseEntity<List<ParentMenuResponse>> getParentMenuList(){
        return parentMenuService.getParentMenus();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteParentMenuById(@PathVariable Long id) {
        return parentMenuService.deleteParentMenu(id);
    }

    @PutMapping("/save")
    public ResponseEntity<?> createOrUpdateParentMenu(@RequestBody MenuUpdateRequest menuUpdateRequest) {
        return parentMenuService.createOrUpdateParentMenu(menuUpdateRequest);
    }

}
