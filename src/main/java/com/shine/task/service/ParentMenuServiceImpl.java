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

    // 등록
    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }

    // 수정
    public void updateMenu(Long id, MenuUpdateRequest menuUpdateRequest) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));

        menu.setName(menuUpdateRequest.getName());
        menu.setListOrder(menuUpdateRequest.getListOrder());
        menu.setComment(menuUpdateRequest.getComment());

        menuRepository.save(menu);
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createOrUpdateParentMenu(MenuUpdateRequest menuUpdateRequest) {
        String name = menuUpdateRequest.getName();
        boolean exists = menuRepository.existsByName(name);

        if(exists) {
            // 수정
            Menu existingMenu = menuRepository.findByName(name);
            updateMenu(existingMenu.getId(), menuUpdateRequest);
        } else {
            // 등록
            Menu newMenu = Menu.builder()
                    .name(menuUpdateRequest.getName())
                    .listOrder(menuUpdateRequest.getListOrder())
                    .comment(menuUpdateRequest.getComment())
                    .build();
            saveMenu(newMenu);
        }

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response("Create or Update Menu Success")
                        .status("Success")
                        .build());
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deleteParentMenu(Long id) {
        boolean existMenu = menuRepository.existsById(id);
        int countChildMenus = menuRepository.countByParentIdIsNotNull(id);

        if(!existMenu) {
            return ResponseEntity.badRequest()
                    .body(CommonResponse.builder()
                            .response("Delete Fail (Not Found Menu)")
                            .status("Fail")
                            .build());
        } else if(countChildMenus > 0) {
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
