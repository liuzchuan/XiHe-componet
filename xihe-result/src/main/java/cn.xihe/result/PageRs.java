package cn.xihe.result;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liuzuochuan
 */
public class PageRs<T> {

    private long pages;
    private long total;
    private List<T> data;
    private List<String> columns;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public PageRs(long pages, long total, List<T> data) {
        this.pages = pages;
        this.total = total;
        this.data = data;
    }

    public <R> PageRs<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getData().parallelStream().map(mapper).collect(Collectors.toList());
        return new PageRs<>(this.getPages(), this.getTotal(), collect);
    }

    public PageRs() {
    }
}