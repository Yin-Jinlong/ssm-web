package cn.yjl.annotations

/**
 *  需要登录
 *
 *  用在方法上，标识该方法需要登录后才能调用
 *
 *  @author YJL
 */
@Target(AnnotationTarget.FUNCTION)
annotation class ShouldLogin
