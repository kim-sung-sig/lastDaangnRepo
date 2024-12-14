package com.demo.daangn.app.common.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponse<T> {

    private List<T> list;

    private Long totalCount;

    private Boolean hasNext;

    private Long lastId;
}
