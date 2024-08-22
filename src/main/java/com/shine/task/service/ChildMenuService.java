package com.shine.task.service;

import com.shine.task.dto.response.ChildMenuResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChildMenuService {
    ResponseEntity<List<ChildMenuResponse>> getChildMenus(Long parentId);
    ResponseEntity<List<ChildMenuResponse>> getChildMenusByParentId(Long parentId);
}
