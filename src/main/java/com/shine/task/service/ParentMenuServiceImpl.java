package com.shine.task.service;

import com.shine.task.common.CommonResponse;
import com.shine.task.dto.result.MenuResult;
import com.shine.task.dto.request.MenuUpdateRequest;
import com.shine.task.dto.response.ParentMenuResponse;
import com.shine.task.entity.Menu;
import com.shine.task.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParentMenuServiceImpl implements ParentMenuService {

    private final MenuRepository menuRepository;

    // 테스트(전체메뉴 목록)
    @Override
    public List<MenuResult> getMenus() {
        final List<Menu> all = menuRepository.findAllByParentIsNull();
        return all.stream().map(MenuResult::new).collect(Collectors.toList());
    }

    // 상위 메뉴 목록
    @Override
    public ResponseEntity<List<ParentMenuResponse>> getParentMenus() {
        List<Menu> menuList = menuRepository.findAllByParentIsNull();

        List<ParentMenuResponse> parentMenuResponses = menuList.stream()
                .map(menu -> ParentMenuResponse.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .listOrder(menu.getListOrder())
                        .comment(menu.getComment())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(parentMenuResponses);
    }

    // 순서 변경
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> updateListOrder(List<MenuUpdateRequest> menuUpdateRequest) {
        try {
            for (MenuUpdateRequest request : menuUpdateRequest) {
                menuRepository.updateListOrder(request.getId(), request.getListOrder());
            }
            return ResponseEntity.ok()
                    .body(CommonResponse.builder()
                            .response("Update list order success")
                            .status("Success")
                            .build());
        } catch (Exception e) {
            log.error("Error updating list order", e);
            return ResponseEntity.internalServerError()
                    .body(CommonResponse.builder()
                            .response("Update list order failed")
                            .status("Fail")
                            .build());
        }
    }

    // name 유효성 체크
    private ResponseEntity<CommonResponse> validateMenuRequest(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(CommonResponse.builder()
                    .response("Menu name cannot be null or empty")
                    .status("Fail")
                    .build());
        }

        if (menuRepository.existsByName(name)) {
            return ResponseEntity.badRequest().body(CommonResponse.builder()
                    .response("Menu name already exists")
                    .status("Fail")
                    .build());
        }
        return null;
    }

    // 등록
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createParentMenu(MenuUpdateRequest menuUpdateRequest) {
        String name = menuUpdateRequest.getName();

        ResponseEntity<CommonResponse> CheckedResponse = validateMenuRequest(name);
        if (CheckedResponse != null) {
            return CheckedResponse;
        }

        // 마지막순서에 1을 더한 값을 등록
        Integer defineListOrder = menuRepository.findLastListOrder() + 1;

        Menu newMenu = Menu.builder()
                .name(menuUpdateRequest.getName())
                .listOrder(defineListOrder)
                .comment(menuUpdateRequest.getComment())
                .build();
        menuRepository.save(newMenu);

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response("Create menu success")
                        .status("Success")
                        .build());
    }

    // 수정
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> updateParentMenu(Long id, MenuUpdateRequest menuUpdateRequest) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));

        String newName = menuUpdateRequest.getName();
        if (newName == null || newName.trim().isEmpty()) {
            newName = menu.getName();
        } else {
            ResponseEntity<CommonResponse> CheckedResponse = validateMenuRequest(newName);
            if (CheckedResponse != null && !newName.equals(menu.getName())) {
                return CheckedResponse;
            }
        }

        menu.setName(newName);
        menu.setComment(menuUpdateRequest.getComment());
        menuRepository.save(menu);

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response("Update menu success")
                        .status("Success")
                        .build());
    }

    // 삭제
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deleteParentMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delete Fail (Not Found Menu)"));

        int countChildMenus = menuRepository.countByParentIdIsNotNull(menu.getId());

        if (countChildMenus > 0) {
            return ResponseEntity.badRequest()
                    .body(CommonResponse.builder()
                            .response("Delete Fail (Menu has child menus)")
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
