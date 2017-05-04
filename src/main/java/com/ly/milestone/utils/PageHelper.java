package com.ly.milestone.utils;

import java.util.List;

/**
 * Created by lyc28724 on 2017/4/5.
 */
public class PageHelper<T> {

    private List<T> content;
    private long pageSize;
    private long pageIndex;
    private long totalCount;
    private long totalPage;
    private long from;
    private T condition;

    public PageHelper() {

    }

    public PageHelper(long pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        pageSize = pageSize == 0 ? 10 : pageSize;
        from = pageSize * (pageIndex - 1);
        this.pageSize = pageSize;
    }

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        pageSize = pageSize == 0 ? 10 : pageSize;
        from = pageSize * (pageIndex - 1);
        this.pageIndex = pageIndex;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        double totalPage = (double) totalCount / (double) getPageSize();
        this.totalPage = (long) Math.ceil(totalPage);
    }

    public long getTotalPage() {
        double totalPage = (double) totalCount / (double) getPageSize();
        return (long) Math.ceil(totalPage);
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

}
