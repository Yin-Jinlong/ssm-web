package cn.yjl.service.exception

/**
 *
 * @author YJL
 */
class DeleteMsgException(
    msg: String,
    cause: Throwable? = null
) : ServiceException(msg, cause)