package cn.yjl.resp

/**
 * 预定义的返回代码和消息
 */
enum class RespCode(val code: Int, val msg: String) {
    OK(0, "OK"),
    NOTHING(0, "Nothing"),
    VALIDATE_FAILED(10, "参数校验失败"),
    USER_LOGIN_SUCCESS(0, "用户登录成功"),
    USER_FAILED_LOGIN(100, "用户名或密码错误"),
    USER_NOT_FOUND(110, "用户不存在"),
    USER_NAME_ERROR(120, "用户名错误"),
    USER_PWD_ERROR(121, "密码格式错误"),

    USER_LOGON_OK(0, "注册成功"),
    USER_LOGON_UNAME_EXISTS(130, "用户名已存在"),

    SERVER_ERROR(1000, "服务器错误"),
}