package com.shine.task.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuUpdateRequest {
    private Long id;
    private Long parentId;
    private String name;
    private String childName;
    private int listOrder;
    private String comment;
}
