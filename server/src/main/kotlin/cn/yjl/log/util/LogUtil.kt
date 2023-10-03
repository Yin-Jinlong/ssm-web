package cn.yjl.log.util

import java.util.logging.Logger

/**
 * 获取当前类名称的Logger
 */
fun Any.getLogger(): Logger = Logger.getLogger(this::class.simpleName)