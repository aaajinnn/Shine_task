package com.shine.task.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuUpdateRequest {
    private Long id;
    private Long parent_id;
    private String name;
    private int listOrder;
    private String comment;
}
