package org.lyle.response;

import lombok.Getter;

/**
 * 响应码枚举，对应HTTP状态码
 */
@Getter
public enum RC {

    SUCCESS(200, "成功"),//成功
    //FAIL(400, "失败"),//失败
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "认证失败"),//未认证
    NOT_FOUND(404, "接口不存在"),//接口不存在
    FAIL(500, "系统繁忙"),//服务器内部错误
    METHOD_NOT_ALLOWED(405,"方法不被允许"),

    /*参数错误:1001-1999*/
    PARAMS_IS_INVALID(1001, "参数无效"),
    PARAMS_IS_BLANK(1002, "参数为空");
    /*用户错误2001-2999*/


    private Integer code;
    private String message;

    RC(int code, String message) {
        this.code = code;
        this.message = message;
    }
}