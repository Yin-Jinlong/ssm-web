package cn.yjl.service.exception

/**
 *
 * @author YJL
 */
class AddMsgException(
    msg: String,
    cause: Throwable? = null
) : ServiceException(msg, cause)