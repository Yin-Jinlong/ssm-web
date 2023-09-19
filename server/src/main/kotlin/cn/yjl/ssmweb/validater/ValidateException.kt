package cn.yjl.ssmweb.validater

import cn.yjl.resp.RespCode

/**
 * 验证异常
 *
 * 附带返回码和消息
 */
class ValidateException(
    val code: Int,
    val msg: String,
    cause: Throwable?
) : Exception(msg, cause) {
    constructor(respCode: RespCode) : this(respCode.code, respCode.msg, null)
}