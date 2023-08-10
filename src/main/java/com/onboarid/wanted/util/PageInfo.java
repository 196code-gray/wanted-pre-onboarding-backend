package com.onboarid.wanted.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class PageInfo {
    private int page;
    private int size;
    private long totalElement;
    private long totalPage;
}
