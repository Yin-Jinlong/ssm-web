package cn.yjl.resp

open class ErrorRespJson(
    respCode: RespCode
) : BaseRespJson(respCode.code, respCode.msg)