package cn.xihe.result.base;


import cn.hutool.core.date.DatePattern;
import cn.xihe.result.enums.CommonResponse;
import cn.xihe.result.enums.RsResponse;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 响应对象基础类
 *
 * @author liuzuochuan
 * @date 2023-06-25
 */
@Data
public class BaseResponse {

    protected String code;
    protected String message;
    protected String traceId;

    protected String date;

    private String success;


    public BaseResponse() {
        this.code = CommonResponse.SUCCESS.getCode();
        this.message = CommonResponse.SUCCESS.getMessage();
        this.success = CommonResponse.SUCCESS.getSuccess();
        this.date = DatePattern.NORM_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    public BaseResponse(RsResponse response) {
        this.code = response.getCode();
        this.message = response.getMessage();
        this.success = response.getSuccess();
        this.date = DatePattern.NORM_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    public BaseResponse(String code, String message, String success) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.date = DatePattern.NORM_DATETIME_FORMATTER.format(LocalDateTime.now());
    }
}
