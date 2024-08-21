package com.shine.task.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildMenuResponse {
    private Long id;
    private Long parent_id;
    private String name;
    private int listOrder;
    private String comment;
}
