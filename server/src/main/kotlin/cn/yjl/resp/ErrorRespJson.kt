package cn.yjl.resp

/**
 * 错误消息返回
 *
 * @author YJL
 */
open class ErrorRespJson(
    code: Int,
    msg: String
) : BaseRespJson(code, msg) {

    /**
     * 使用预定义
     *
     * @param respCode 预定义
     * @see RespCode
     */
    constructor(respCode: RespCode) : this(respCode.code, respCode.msg)
}