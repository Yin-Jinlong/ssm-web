package cn.yjl.service.exception

/**
 *  服务器异常
 *
 *  @author YJL
 */
open class ServiceException(
    msg: String,
    cause: Throwable? = null
) : RuntimeException(msg, cause)