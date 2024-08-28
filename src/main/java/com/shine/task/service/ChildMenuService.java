package com.shine.task.service;

import com.shine.task.common.CommonResponse;
import com.shine.task.dto.request.MenuUpdateRequest;
import com.shine.task.dto.response.ChildMenuResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChildMenuService {
    ResponseEntity<List<ChildMenuResponse>> getChildMenus(Long parentId);
    ResponseEntity<CommonResponse> createChildMenu(Long parentId, MenuUpdateRequest menuUpdateRequest);
    ResponseEntity<CommonResponse> updateChildMenu(Long id, MenuUpdateRequest menuUpdateRequest);
    ResponseEntity<CommonResponse> deleteChildMenu(Long id);
}
