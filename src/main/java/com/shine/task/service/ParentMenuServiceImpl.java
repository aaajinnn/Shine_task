package com.shine.task.service;

import com.shine.task.common.CommonResponse;
import com.shine.task.common.ValidationResponse;
import com.shine.task.dto.request.MenuUpdateRequest;
import com.shine.task.dto.response.ParentMenuResponse;
import com.shine.task.dto.result.MenuResult;
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
public class ParentMenuServiceImpl implements ParentMenuService {

    private final MenuRepository menuRepository;
    private final ValidationResponse validationResponse;

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

            List<Menu> menuList = new ArrayList<>();

            // UpdateListOrder를 가져와서 할 필요없이 이렇게 saveAll()을 이용하여 update 할 수 있음!!
            // save와 saveAll의 차이 ?
            // save()를 여러번 호출하는 경우는 계속 기존의 트랜잭션이 존재하는 지 계속 확인해줘야 하기 때문에 추가로 리소스가 소모됨
            // 하지만 saveAll()의 내부에서는 save()를 호출한다. => saveAll()을 할 때 트랜잭션이 생성되어 "하나의 트랜잭션"으로 작동한다. => 때문에 save()를 여러번 하는 것보다 성능이 더 좋다.
            for (MenuUpdateRequest request : menuUpdateRequest) {
                Menu menu = menuRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Menu not found"));
                menu.setListOrder(request.getListOrder());
                menuList.add(menu);
            }

            menuRepository.saveAll(menuList);

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

    // 등록
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createParentMenu(MenuUpdateRequest menuUpdateRequest) {
        List<Menu> menuList = new ArrayList<>();

        String name = menuUpdateRequest.getName();

        ResponseEntity<CommonResponse> CheckedResponse = validationResponse.checkMenuName(name);
        if (CheckedResponse != null) {
            return CheckedResponse;
        }

        // 마지막순서에 1을 더한 값을 등록
        Integer defineListOrder = menuRepository.findLastOrderForParent() + 1;

        Menu newMenu = Menu.builder()
                .name(menuUpdateRequest.getName())
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
    public ResponseEntity<CommonResponse> updateParentMenu(Long id, MenuUpdateRequest menuUpdateRequest) {
        List<Menu> menuList = new ArrayList<>();

        Menu menu = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));

        String newName = menuUpdateRequest.getName();
        ResponseEntity<CommonResponse> CheckedResponse = validationResponse.checkMenuName(newName);
            if (CheckedResponse != null && !newName.equals(menu.getName())) {
                return CheckedResponse;
            }

        menu.setName(newName);
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
    public ResponseEntity<CommonResponse> deleteParentMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delete Fail (Not Found Menu)"));

        int countChildMenus = menuRepository.countByParentIdIsNotNull(menu.getId());

        if (countChildMenus > 0) {
            return ResponseEntity.status(442).body(CommonResponse.builder()
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
