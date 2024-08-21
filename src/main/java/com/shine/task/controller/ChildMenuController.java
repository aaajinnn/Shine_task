package com.shine.task.controller;

import com.shine.task.dto.rsponse.ChildMenuResponse;
import com.shine.task.service.ChildMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/menu/child")
public class ChildMenuController {

    private final ChildMenuService childMenuService;

    // 목록
    @GetMapping("/list")
    public ResponseEntity<List<ChildMenuResponse>> getParentMenuList(){
        return childMenuService.getChildMenus();
    }

}
