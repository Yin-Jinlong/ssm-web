package cn.yjl.service.exception

import cn.yjl.resp.ResponseJson

/**
 *
 * @author YJL
 */
class ServiceExceptionWithJson(
    msg: String,
    val json: ResponseJson,
    cause: Throwable? = null
) : ServiceException(msg, cause)