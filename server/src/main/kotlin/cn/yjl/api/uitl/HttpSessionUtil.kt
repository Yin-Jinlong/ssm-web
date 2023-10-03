package cn.yjl.api.uitl

import cn.yjl.api.UserApi
import cn.yjl.util.now
import jakarta.servlet.http.HttpSession


/**
 *  保存用户信息
 *
 * @param uid 用户id
 * @param pwd 用户密码
 */
fun HttpSession.save(uid: Int, pwd: String) {
    setAttribute(UserApi.SESSION_LOGGED_TIME, now())
    setAttribute(UserApi.SESSION_USER_ID, uid.toString())
    setAttribute(UserApi.SESSION_USER_PWD, pwd)
}


/**
 * 清除所有保存信息
 */
fun HttpSession.clearAll() {
    removeAttribute(UserApi.SESSION_USER_ID)
    removeAttribute(UserApi.SESSION_USER_PWD)
    removeAttribute(UserApi.SESSION_LOGGED_TIME)
}


/**
 * 从Session获取密码
 */
fun HttpSession.getPwd() = getAttribute(UserApi.SESSION_USER_PWD) as String?

/**
 * 从Session获取用户名
 */
fun HttpSession.getUid() = getAttribute(UserApi.SESSION_USER_ID) as String?

/**
 * 是否登录保持已过期
 */
fun HttpSession.isOutOfDate(): Boolean {
    val time: Long? = getAttribute(UserApi.SESSION_LOGGED_TIME) as Long?
    return time == null || now() - time > 5 * 60 * 1000L
}

/**
 * 更新活跃时间
 */
fun HttpSession.updateTime() = setAttribute(UserApi.SESSION_LOGGED_TIME, now())