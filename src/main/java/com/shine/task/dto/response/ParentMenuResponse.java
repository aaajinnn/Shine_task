package com.shine.task.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParentMenuResponse {
    private Long id;
    private String name;
    private Integer listOrder;
    private String comment;
}
