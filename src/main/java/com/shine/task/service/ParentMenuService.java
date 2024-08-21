package com.shine.task.service;

import com.shine.task.dto.result.MenuResult;
import com.shine.task.dto.rsponse.ParentMenuResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParentMenuService {
    // 테스트(전체메뉴 목록)
    List<MenuResult> getMenus();

    ResponseEntity<List<ParentMenuResponse>> getParentMenus();
}
