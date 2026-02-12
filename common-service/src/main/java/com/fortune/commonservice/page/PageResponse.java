package com.fortune.commonservice.page;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 当前页（从 1 开始） */
    private long pageNo;

    /** 每页大小 */
    private long pageSize;

    /** 总记录数 */
    private long total;

    /** 总页数 */
    private long totalPages;

    /** 当前页数据 */
    private List<T> records;

    /* ================= 构造方法 ================= */

    private PageResponse(long pageNo, long pageSize, long total, List<T> records) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPages = calcTotalPages(total, pageSize);
        this.records = records;
    }

    /** 空分页 */
    public static <T> PageResponse<T> empty(long pageNo, long pageSize) {
        return new PageResponse<>(pageNo, pageSize, 0, Collections.emptyList());
    }

    /** 通用构建 */
    public static <T> PageResponse<T> of(
            long pageNo,
            long pageSize,
            long total,
            List<T> list) {

        return new PageResponse<>(pageNo, pageSize, total, list);
    }


    /* ================= 高级能力 ================= */

    /** DTO 转换（不重新分页） */
    public <R> PageResponse<R> map(Function<T, R> mapper) {
        List<R> newList = records == null
                ? Collections.emptyList()
                : records.stream().map(mapper).collect(Collectors.toList());

        return PageResponse.of(pageNo, pageSize, total, newList);
    }

    /* ================= 私有方法 ================= */

    private static long calcTotalPages(long total, long pageSize) {
        if (pageSize <= 0) {
            return 0;
        }
        return (total + pageSize - 1) / pageSize;
    }
}
