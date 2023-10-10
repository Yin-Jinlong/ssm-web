package cn.yjl.annotations.json

/**
 * 忽略序列化
 *
 * @author YJL
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.TYPE, AnnotationTarget.CLASS)
annotation class JsonIgnored
