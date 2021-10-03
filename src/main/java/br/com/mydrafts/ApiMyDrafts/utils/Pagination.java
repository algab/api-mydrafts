package br.com.mydrafts.ApiMyDrafts.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Pagination {

    public static <T> PageImpl<T> applyPage(List<T> content, Pageable page) {
        Integer initSize = initPage(Long.valueOf(page.getOffset()).intValue(), content.size());
        Integer endSize = endPage(initSize, page.getPageSize(), content.size());
        return new PageImpl<>(content.subList(initSize, endSize), page, content.size());
    }

    public static Integer initPage(Integer initSize, Integer total) {
        if (initSize > total) {
            Integer rest = initSize - total;
            initSize -= rest;
        }
        return initSize;
    }

    private static Integer endPage(Integer initSize, Integer pageSize, Integer total) {
        Integer endSize = initSize + pageSize;
        if (endSize > total) {
            Integer rest = endSize - total;
            endSize -= rest;
        } else {
            endSize = pageSize;
        }
        return endSize;
    }

}
