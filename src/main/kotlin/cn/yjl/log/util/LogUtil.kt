package cn.yjl.log.util

import java.util.logging.Logger

fun Any.getLogger(): Logger = Logger.getLogger(this.javaClass.name)