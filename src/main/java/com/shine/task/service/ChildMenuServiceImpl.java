package com.shine.task.service;

import com.shine.task.dto.response.ChildMenuResponse;
import com.shine.task.entity.Menu;
import com.shine.task.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChildMenuServiceImpl implements ChildMenuService {

    private final MenuRepository menuRepository;

    // 하위 메뉴 목록
    @Override
    public ResponseEntity<List<ChildMenuResponse>> getChildMenus(Long parentId) {
        List<Menu> menuList = menuRepository.findByParentId(parentId);

        List<ChildMenuResponse> childMenuResponses = menuList.stream()
                .map(menu -> ChildMenuResponse.builder()
                        .id(menu.getId())
                        .parent_id(menu.getParent().getId())
                        .name(menu.getName())
                        .listOrder(menu.getListOrder())
                        .comment(menu.getComment())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(childMenuResponses);
    }

    // 상위 메뉴 parent_id 에 해당하는 이름 가져오기
    @Override
    public ResponseEntity<List<ChildMenuResponse>> getChildMenusByParentId(Long parentId) {
        return null;
    }
}
