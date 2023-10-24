package cn.yjl.service.util

import cn.yjl.service.ServiceException

internal fun assertRowChange(rc: Int, target: Int = 1, msg: () -> String) {
    if (rc != target)
        throw ServiceException(msg())
}

internal fun assertRowChangeError(rc: Int, target: Int = 1, error: () -> ServiceException) {
    if (rc != target)
        throw error()
}
