package com.farmu.api.challenge.common.utils;

import com.farmu.api.challenge.common.dto.api.ApiPageTO;
import com.farmu.api.challenge.common.dto.enums.PageType;
import com.farmu.api.challenge.common.dto.internal.PageTO;
import lombok.NoArgsConstructor;

import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.size;

@NoArgsConstructor
public class PageHelper {

    public static <T> PageTO<T> newPage(final ApiPageTO<T> apiPage) {
        return PageTO.<T>builder().type(apiPage.getType()).elements(apiPage.getElements()).build();
    }

    public static <T> PageTO<T> newPage(final List<T> elements, final int pageSize) {
        return new PageTO<>(definePositionBySize(size(elements), pageSize), elements);
    }

    public static <T> PageTO<T> newPage(final List<T> elements) {
        return new PageTO<>(definePositionIfEmpty(size(elements)), elements);
    }

    public static PageType definePositionBySize(final int lastPageSize, final int pageSize) {
        return lastPageSize < pageSize ? PageType.FINAL : PageType.PARTIAL;
    }

    public static int defineIterationsBySize(final int pageSize, final int partialPageSize) {
        if (pageSize < 1) {
            return 0;
        }
        return (pageSize + partialPageSize - 1) / partialPageSize;
    }

    public static int defineLastPageBySize(final int firstPage, final int pageSize, final int partialPageSize) {
        if (pageSize < 1) {
            return firstPage;
        }
        return firstPage + defineIterationsBySize(pageSize, partialPageSize) - 1;
    }

    public static PageType definePositionIfEmpty(final int resultSize) {
        return resultSize > 0 ? PageType.PARTIAL : PageType.FINAL ;
    }

}