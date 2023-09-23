package cn.yjl.annotations

/**
 * 仅服务端使用
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
annotation class ServerUse()
