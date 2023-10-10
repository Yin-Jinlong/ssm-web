package cn.yjl.service

/**
 *  服务器异常
 *
 *  @author YJL
 */
class ServiceException(
    msg: String,
    cause: Throwable? = null
) : RuntimeException(msg, cause)