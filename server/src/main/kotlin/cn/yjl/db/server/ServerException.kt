package cn.yjl.db.server

/**
 *  服务器异常
 *
 *  @author YJL
 */
class ServerException(
    msg: String,
    cause: Throwable? = null
) : RuntimeException(msg, cause)