package cn.xihe.result;


import cn.xihe.result.base.BaseResponse;

/**
 * 通用响应对象
 *
 * @author liuzuochuan
 * @date 2023-06-25
 */
public class CommonResponse<T> extends BaseResponse {

    /**
     * 数据列表
     */
    protected T data;


    public CommonResponse() {
        super();
    }

    public CommonResponse(T data) {
        super();
        this.data = data;
    }
}
