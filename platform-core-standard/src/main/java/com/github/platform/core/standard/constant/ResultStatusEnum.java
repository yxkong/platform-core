package com.github.platform.core.standard.constant;


import com.github.platform.core.standard.exception.BaseResult;

/**
 * 通用异常
 * 1~ 1000 之间
 * @author yxkong
 */
public enum ResultStatusEnum implements BaseResult {
    /**************通用状态 start***************/
    /**
     * 成功状态1
     */
    SUCCESS("1", "成功！"),
    /**
     * 失败状态0
     */
    ERROR("0", "系统异常，请稍后再试！"),



    /** 通用异常  1000 ~ 1030*/

    NO_DATA("1000", "未查询到相关数据"),
    PARAM_EMPTY("1001", "必填项参数为空"),
    PARAM_ERROR("1002", "请求参数异常"),
    SOURCE_ERROR("1003", "来源错误"),
    NO_AUTH("1004","您没有操作权限"),

    NO_DATA_AUTH("1004", "无数据操作权限"),
    SIGN("1005", "验签失败"),
    SIGN_FAIL("1005", "验签失败"),
    REPEAT("1006", "重复提交，请稍后再试！"),
    REPEAT_BIZ("1006", "其他用户正在操作，请您稍后再试！"),
    BAD_REQUEST("1007", "非法请求"),
    TOKEN_INVALID("1008", "token无效，请重新登录"),
    TIME_INVALID("1009", "请求的时间戳t超过30分钟"),
    CODE_OFF("1010", "已禁用"),
    UNRECOGNIZED("1012", "参数值不在处理的范围内"),
    CHECKEXCEPTION("1013", "数据校验失败"),
    NO_FOUND_IMPLEMENT("1014", "未找到对应的实现类"),
    /**
     * 请求方式异常
     */
    REQ_TYPE_EXCEPTION("1015", "请求方式异常"),
    VALID_EXCEPTION("1016", "数据校验异常"),

    CONFIG_ERROR("1017", "未找到对应：%s 的配置，请先配置"),
    GRAY("1018", "产品维护中，请您稍后再试"),


    COMMON_INSERT_ERROR("1020","未能成功添加数据！"),
    UPDATE_ID_IS_NULL("1021","更新时，主键不能为空！" ),
    COMMON_UPDATE_ERROR("1022","未能成功修改数据！"),
    COMMON_DELETE_ERROR("1023","未能成功删除数据"),
    COMMON_BIND_EXCEPTION("1024", "绑定参数异常"),
    COMMON_UPLOAD_STREAM_EXCEPTION("1025", "上传文件获取文件流异常！"),
    /**
     * 请求内容类型如果不是json，或者为json单请求内容为空，会捕获该异常
     */
    COMMON_NOT_READABLE_EXCEPTION("1026", "请求内容中的JSON，无法解析，请排查！"),
    COMMON_NOT_WRITABLE_EXCEPTION("1027", "响应内容无法解析为json，请排查！"),

    /** DB 相关异常，从1100 ~ 1199 **/
    DB_EXCEPTION("1100", "数据库异常"),
    DB_BAD_SQL("1101","数据库脚本异常！"),

    DB_CANNOT_GET_CONNECTION("1102","无法获取数据库连接！"),
    DB_QUERY_TIMEOUT("1103","数据查询异常！"),
    DB_DUPLICATE_KEY("1104","数据库唯一索引冲突！"),
    DB_DEAD_LOCK("1105","数据死锁！"),
    DB_CANNOT_ACQUIRE_LOCK("1106","无法获取锁！"),


    /** rpc 相关异常，从1200 ~ 1299 **/
    RPC_CONNECTION_TIME_OUT("1200","连接超时"),
    REMOTE_SERVICE_EXCEPTION("1201", "服务调用返回失败"),
    RPC_READ_TIME_OUT("1202","读取超时"),
    /** 消息中间件相关异常，从1300 ~ 1399 **/

    ;
    private String status;

    private String message;

    ResultStatusEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }
    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
