package cn.xihe.result.pojo;

/**
 * 页签证官 分页对象
 *
 * @author liuzuochuan
 * @date 2023-06-25
 */
public class PageVo {

    private int page;
    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int limit) {
        this.size = limit;
    }
}
