package com.shine.task.dto.result;

import com.shine.task.entity.Menu;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MenuResult {
    private final Long id;
    private final String name;
    private final int listOrder;
    private final String comment;
    private final List<MenuResult> children;

    public MenuResult(final Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.listOrder = menu.getListOrder();
        this.comment = menu.getComment();
        this.children = menu.getChildren().stream().map(MenuResult::new)
                .sorted(Comparator.comparingInt(MenuResult::getListOrder)) //children의 listOrder 정렬 추가
                .collect(Collectors.toList());
    }
}
