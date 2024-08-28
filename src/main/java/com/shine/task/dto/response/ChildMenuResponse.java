package com.shine.task.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildMenuResponse {
    private Long id;
    private Long parentId;
    private String parentName;
    private String childName;
    private int listOrder;
    private String comment;
}
