package br.com.mydrafts.apimydrafts.utils;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class Pagination {

    private Pagination() {}

    public static <T> PageImpl<T> applyPage(List<T> content, Pageable page) {
        Integer initSize = initPage((int) page.getOffset(), content.size());
        Integer endSize = endPage(initSize, page.getPageSize(), content.size());
        return new PageImpl<>(content.subList(initSize, endSize), page, content.size());
    }

    private static Integer initPage(Integer initSize, Integer total) {
        if (initSize > total) {
            int rest = initSize - total;
            initSize -= rest;
        }
        return initSize;
    }

    private static Integer endPage(Integer initSize, Integer pageSize, Integer total) {
        int endSize = initSize + pageSize;
        if (endSize >= total) {
            int rest = endSize - total;
            endSize -= rest;
        } else {
            endSize = pageSize;
        }
        return endSize;
    }

}
