package com.shine.task.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponse {
    private String response;
    private String status;
}
