package cn.yjl.util.log

import java.util.logging.Logger

fun Any.getLogger(): Logger = Logger.getLogger(this::class.simpleName)