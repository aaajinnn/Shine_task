package com.shine.task.service;

import com.shine.task.common.CommonResponse;
import com.shine.task.dto.result.MenuResult;
import com.shine.task.dto.request.MenuUpdateRequest;
import com.shine.task.dto.response.ParentMenuResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParentMenuService {
    // 테스트(전체메뉴 목록)
    List<MenuResult> getMenus();

    ResponseEntity<List<ParentMenuResponse>> getParentMenus();
    ResponseEntity<CommonResponse> updateListOrder(List<MenuUpdateRequest> updateMenu);

    ResponseEntity<CommonResponse> createParentMenu(MenuUpdateRequest menuUpdateRequest);
    ResponseEntity<CommonResponse> updateParentMenu(Long id, MenuUpdateRequest menuUpdateRequest);
    ResponseEntity<CommonResponse> deleteParentMenu(Long id);
}
