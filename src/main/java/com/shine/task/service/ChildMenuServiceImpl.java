package com.shine.task.service;

import com.shine.task.common.CommonResponse;
import com.shine.task.common.ValidationResponse;
import com.shine.task.dto.request.MenuUpdateRequest;
import com.shine.task.dto.response.ChildMenuResponse;
import com.shine.task.entity.Menu;
import com.shine.task.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChildMenuServiceImpl implements ChildMenuService {

    private final MenuRepository menuRepository;
    private final ValidationResponse validationResponse;

    // 하위 메뉴 목록
    @Override
    public ResponseEntity<List<ChildMenuResponse>> getChildMenus(Long parentId) {
        List<Menu> menuList = menuRepository.findByParentId(parentId);

        List<ChildMenuResponse> childMenuResponses = menuList.stream()
                .map(menu -> ChildMenuResponse.builder()
                        .id(menu.getId())
                        .parentId(menu.getParent().getId())
                        .parentName(menu.getParent().getName())
                        .childName(menu.getName())
                        .listOrder(menu.getListOrder())
                        .comment(menu.getComment())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(childMenuResponses);
    }

    // 등록
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createChildMenu(Long parentId, MenuUpdateRequest menuUpdateRequest) {
        List<Menu> menuList = new ArrayList<>();

        String name = menuUpdateRequest.getChildName();
        Integer listOrder = menuRepository.findLastOrderForChild(parentId);

        ResponseEntity<CommonResponse> CheckedResponse = validationResponse.checkMenuName(name);
        if (CheckedResponse != null) {
            return CheckedResponse;
        }

        Menu parentMenu = menuRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent menu not found"));

        int defineListOrder;
        if (listOrder != null) {
            // 마지막순서에 1을 더한 값을 등록
            defineListOrder = listOrder + 1;
        } else {
            defineListOrder = 1;
        }

        Menu newMenu = Menu.builder()
                .name(menuUpdateRequest.getChildName())
                .parent(parentMenu)
                .listOrder(defineListOrder)
                .comment(menuUpdateRequest.getComment())
                .build();
        menuList.add(newMenu);

        menuRepository.saveAll(menuList);

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response("Create menu success")
                        .status("Success")
                        .build());
    }

    // 수정
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> updateChildMenu(Long id, MenuUpdateRequest menuUpdateRequest) {
        List<Menu> menuList = new ArrayList<>();

        Menu menu = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));

        String newChildName = menuUpdateRequest.getChildName();
        ResponseEntity<CommonResponse> CheckedResponse = validationResponse.checkMenuName(newChildName);
        if (CheckedResponse != null && !newChildName.equals(menu.getName())) {
            return CheckedResponse;
        }

        menu.setName(newChildName);
        menu.setComment(menuUpdateRequest.getComment());
        menuList.add(menu);
        menuRepository.saveAll(menuList);

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response("Update menu success")
                        .status("Success")
                        .build());
    }

    // 삭제
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deleteChildMenu(Long id) {
        menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Delete Fail (Not Found Menu)"));
        if(!menuRepository.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body(CommonResponse.builder()
                            .response("Delete Fail (This id does not exist)")
                            .status("Fail")
                            .build());
        }
        menuRepository.deleteById(id);
        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response("Delete Success")
                        .status("Success")
                        .build());
    }
}
