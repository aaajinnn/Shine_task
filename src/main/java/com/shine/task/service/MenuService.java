package com.shine.task.service;

import com.shine.task.dto.result.MenuResult;
import com.shine.task.dto.rsponse.ChildMenuResponse;
import com.shine.task.dto.rsponse.ParentMenuResponse;
import com.shine.task.entity.Menu;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MenuService {
    // 테스트(전체메뉴 목록)
    List<MenuResult> getMenus();

    ResponseEntity<List<ParentMenuResponse>> getParentMenus();
    //List<ChildMenuResponse> getChildMenus();
}
