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
    setAttribute(UserApi.SESSION_USER_ID, uid.toString())
    setAttribute(UserApi.SESSION_USER_PWD, pwd)
}


/**
 * 清除所有保存信息
 */
fun HttpSession.clearAll() {
    removeAttribute(UserApi.SESSION_USER_ID)
    removeAttribute(UserApi.SESSION_USER_PWD)
}


/**
 * 从Session获取密码
 */
fun HttpSession.getPwd() = getAttribute(UserApi.SESSION_USER_PWD) as String?

/**
 * 从Session获取用户名
 */
fun HttpSession.getUid() = getAttribute(UserApi.SESSION_USER_ID) as String?

fun HttpSession.isLogin() = getAttribute(UserApi.SESSION_USER_ID) != null