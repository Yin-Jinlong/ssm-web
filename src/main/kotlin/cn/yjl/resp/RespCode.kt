package cn.yjl.resp

enum class RespCode(val code: Int, val msg: String) {
    OK(0, "OK"),
    VALIDATE_FAILED(10, "参数校验失败"),
    USER_LOGIN_SUCCESS(0, "用户登录成功"),
    USER_FAILED_LOGIN(100, "用户名或密码错误"),

    SERVER_ERROR(1000, "服务器错误"),
}