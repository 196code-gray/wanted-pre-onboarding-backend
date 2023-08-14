package com.onboarid.wanted.util;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
@Getter
public class MultiResponse<T> {
    private List<T> data;
    private PageInfo page;

    public MultiResponse(List<T> data, Page page) {
        this.data = data;
        this.page = new PageInfo(page.getNumber() +1, page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }
}
