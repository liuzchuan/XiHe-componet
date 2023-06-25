package cn.xihe.result.enums;


/**
 * 常见响应状态码
 *
 * @author liuzuochuan
 * @date 2023-06-25
 */
public enum CommonResponse implements RsResponse {
    SUCCESS("200", "请求成功", "SUCCESS"),
    FAIL("500", "请求失败", "FAIL"),
    ;

    CommonResponse(String code, String message, String success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    private final String code;
    private final String message;
    private final String success;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getSuccess() {
        return this.success;
    }
}
