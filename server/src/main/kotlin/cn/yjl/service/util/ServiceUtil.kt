package cn.yjl.service.util

import cn.yjl.service.ServiceException

internal fun assertRowChange(rc: Int, target: Int = 1, msg: () -> String) {
    if (rc != target)
        throw ServiceException(msg())
}
