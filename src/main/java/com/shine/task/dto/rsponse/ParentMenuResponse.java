package com.shine.task.dto.rsponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParentMenuResponse {
    private Long id;
    private String name;
    private int listOrder;
    private String parentComment;
}
