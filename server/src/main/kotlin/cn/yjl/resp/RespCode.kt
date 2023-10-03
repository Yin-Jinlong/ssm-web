package cn.yjl.resp

/**
 * 预定义的返回代码和消息
 *
 * @author YJL
 */
enum class RespCode(val code: Int, val msg: String) {
    /**
     * OK
     */
    OK(0, "OK"),

    /**
     * 没有附加消息
     */
    NOTHING(0, "Nothing"),

    /**
     * 参数校验失败，格式等不正确
     */
    VALIDATE_FAILED(10, "参数校验失败"),

    /**
     * 用户登录成功
     */
    USER_LOGIN_SUCCESS(0, "用户登录成功"),

    /**
     * 用户登录失败（一般原因）
     */
    USER_FAILED_LOGIN(100, "用户名或密码错误"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(110, "用户不存在"),

    /**
     * 用户名格式错误
     */
    USER_NAME_ERROR(120, "用户名错误"),

    /**
     * 密码格式错误
     */
    USER_PWD_ERROR(121, "密码格式错误"),

    /**
     * 提交消息成功
     */
    USER_MSG_SEND_OK(0, "提交成功"),

    /**
     * 用户未登录
     */
    USER_NOT_LOGIN(130, "用户未登录"),

    /**
     * 注册成功
     */
    USER_LOGON_OK(0, "注册成功"),

    /**
     * 用户名已存在
     */
    USER_LOGON_UNAME_EXISTS(130, "用户名已存在"),

    /**
     * 服务器错误
     */
    SERVER_ERROR(1000, "服务器错误"),
}