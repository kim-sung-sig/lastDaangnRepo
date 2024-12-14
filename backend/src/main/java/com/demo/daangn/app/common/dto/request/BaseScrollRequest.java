package com.demo.daangn.app.common.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BaseScrollRequest {

    protected Long lastItemId;
    protected Integer pageSize;

}
