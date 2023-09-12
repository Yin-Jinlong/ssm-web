package cn.yjl.resp

open class ErrorRespJson(
    code: Int,
    msg: String
) : BaseRespJson(code, msg) {
    constructor(respCode: RespCode) : this(respCode.code, respCode.msg)
}