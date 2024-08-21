package com.shine.task.service;

import com.shine.task.dto.rsponse.ChildMenuResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChildMenuService {
    ResponseEntity<List<ChildMenuResponse>> getChildMenus();
}
