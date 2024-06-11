package com.demo.daangn.global.dto.request;

import lombok.Data;

@Data
public class ScrollRequest {
    Long lastItemId;
    Integer size;
    Long categoryNum;
    String search;
}
