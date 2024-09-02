package com.demo.daangn.domain.personal.navigation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NavigationItem {

    private String title;

    private String url;

    private String icon;

}
