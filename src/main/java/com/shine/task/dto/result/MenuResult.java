package com.shine.task.dto.result;

import com.shine.task.entity.Menu;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MenuResult {
    private final Long id;
    private final String name;
    private final int listOrder;
    private final String parentComment;
    private final String childComment;
    private final List<MenuResult> children;

    public MenuResult(final Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.listOrder = menu.getListOrder();
        this.parentComment = menu.getParentComment();
        this.childComment = menu.getChildComment();
        this.children = menu.getChildren().stream().map(MenuResult::new).collect(Collectors.toList());
    }
}
