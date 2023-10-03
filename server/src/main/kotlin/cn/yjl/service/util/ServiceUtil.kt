package cn.yjl.service.util

import cn.yjl.db.dao.Dao
import cn.yjl.service.BaseService
import org.springframework.context.annotation.Bean

@Bean
inline fun <reified T : Dao> BaseService<T>.getDao(): T = sqlSession.getMapper(T::class.java)