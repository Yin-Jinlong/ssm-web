package cn.yjl.db.server

class ServerException(
    msg: String,
    cause: Throwable? = null
) : RuntimeException(msg, cause) {
}