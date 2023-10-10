package cn.yjl.db

import cn.yjl.annotations.json.JsonIgnored

/**
 * 用户
 *
 * @param uid 用户id
 * @param name 用户名
 */
data class User(
    val uid: Int,
    val name: String,
    @JsonIgnored
    val pwd: String? = null
)