package cn.yjl.service

import cn.yjl.db.dao.Dao
import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired

/**
 * 基础服务
 *
 * 定义所有服务通用的方法
 *
 * @author YJL
 */
@org.springframework.stereotype.Service
abstract class BaseService<T : Dao> : Service {

    @Autowired
    lateinit var sqlSession: SqlSession

    abstract var dao: T

}
