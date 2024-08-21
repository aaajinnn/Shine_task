package com.shine.task.dto.rsponse;

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
