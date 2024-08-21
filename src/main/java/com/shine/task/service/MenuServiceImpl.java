package com.shine.task.service;

import com.shine.task.dto.result.MenuResult;
import com.shine.task.dto.rsponse.ChildMenuResponse;
import com.shine.task.dto.rsponse.ParentMenuResponse;
import com.shine.task.entity.Menu;
import com.shine.task.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    // 테스트(전체메뉴 목록)
    @Override
    public List<MenuResult> getMenus() {
        final List<Menu> all = menuRepository.findAllByParentIsNull();
        return all.stream().map(MenuResult::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<List<ParentMenuResponse>> getParentMenus() {
        List<Menu> menuList = menuRepository.findAllByParentIsNull();

        List<ParentMenuResponse> parentMenuResponses = menuList.stream()
                .map(menu -> ParentMenuResponse.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .listOrder(menu.getListOrder())
                        .parentComment(menu.getParentComment())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(parentMenuResponses);
    }

//    @Override
//    public List<ChildMenuResponse> getChildMenus() {
//        List<Menu> menuList = menuRepository.findAllByParentIsNull();
//
//        return menuList.stream()
//                .map(menu -> ChildMenuResponse.builder()
//                        .id(menu.getId())
//                        .name(menu.getName())
//                        .listOrder(menu.getListOrder())
//                        .childComment(menu.getChildComment())
//                        .build())
//                .collect(Collectors.toList());
//    }
}
