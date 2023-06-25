package cn.xihe.result.enums;

/**
 * 响应接口
 *
 * @author liuzuochuan
 * @date 2023-06-25
 */
public interface RsResponse {

    /**
     * 获取返回码
     *
     * @return 返回码
     */
    String getCode();

    /**
     * 获取返回信息
     *
     * @return 返回信息
     */
    String getMessage();

    /**
     * 获取成功状态状态
     *
     * @return 成功状态
     */
    String getSuccess();

}
