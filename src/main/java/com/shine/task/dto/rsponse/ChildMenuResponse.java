package com.shine.task.dto.rsponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shine.task.dto.result.MenuResult;
import com.shine.task.entity.Menu;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChildMenuResponse {
    private Long id;
    private Long parent_id;
    private String name;
    private int listOrder;
    private String childComment;
}
