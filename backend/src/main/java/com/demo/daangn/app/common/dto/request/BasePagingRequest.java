package com.demo.daangn.app.common.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BasePagingRequest {

    protected Long page;

    protected Long pageSize;

}
