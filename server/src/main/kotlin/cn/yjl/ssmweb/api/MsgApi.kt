package cn.yjl.ssmweb.api

import cn.yjl.db.dao.MsgDao
import cn.yjl.log.util.getLogger
import cn.yjl.resp.ResponseJson
import cn.yjl.resp.msg.MsgRespJson
import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/api/msg", method = [RequestMethod.GET])
class MsgApi {

    val log = getLogger()

    @Autowired
    lateinit var sqlSession: SqlSession

    @PostMapping("/all")
    fun getAll(): ResponseJson {
        val dao = sqlSession.getMapper(MsgDao::class.java)
        val msgs = dao.getAll()
        return MsgRespJson(msgs)
    }

}