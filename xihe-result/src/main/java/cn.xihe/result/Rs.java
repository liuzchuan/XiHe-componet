package cn.xihe.result;


import cn.xihe.result.base.BaseResponse;
import cn.xihe.result.enums.CommonResponse;
import cn.xihe.result.enums.RsResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 统一响应对象
 *
 * @author liuzuochuan
 * @date 2023-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Rs<T> extends BaseResponse {


    private T data;


    public Rs(RsResponse response, T data) {
        super(response);
        this.data = data;
    }

    public Rs(RsResponse response) {
        super(response);
    }

    public Rs(RsResponse response, String message) {
        super(response);
        this.setMessage(message);
    }


    public Rs() {
        super();
    }

    public Rs(T data) {
        super();
        this.data = data;
    }

    public static <T> Rs<T> success(T t) {
        return new Rs<>(t);
    }

    public static <T> Rs<T> success(RsResponse response, T t) {
        return new Rs<>(response, t);
    }

    public static <T> Rs<T> success() {
        return new Rs<>();
    }

    public static <T> Rs<T> success(RsResponse response) {
        return new Rs<>(response);
    }

    public static <T> Rs<T> fail() {
        return new Rs<>(CommonResponse.FAIL);
    }

    /**
     * 失败
     *
     * @param message 消息
     * @return {@link Rs}<{@link T}>
     */
    public static <T> Rs<T> fail(String message) {
        return new Rs<>(CommonResponse.FAIL, message);
    }

}
