package com.onboarid.wanted.util;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

public class MultiResponse<T> {
    private List<T> list;
    private PageInfo page;

    public MultiResponse(List<T> list, Page page) {
        this.list = list;
        this.page = new PageInfo(page.getNumber() +1, page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }
}
