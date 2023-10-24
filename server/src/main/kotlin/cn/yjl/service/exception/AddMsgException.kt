package cn.yjl.service.exception

import cn.yjl.service.ServiceException

/**
 *
 * @author YJL
 */
class AddMsgException(
    msg: String,
    cause: Throwable? = null
) : ServiceException(msg, cause)