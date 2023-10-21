package cn.yjl.test.service

import cn.yjl.service.MsgService
import cn.yjl.ssmweb.SsmWebApplication
import cn.yjl.test.annotation.SSMTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 *
 * @author YJL
 */
@SSMTest([SsmWebApplication::class])
class MsgServiceTest {

    @Autowired
    lateinit var msgService: MsgService

    @Test
    fun testAddDelete(){
        val m=msgService.addMsg(732418,"test")
        if (m==null)
            assert(false)
        msgService.deleteMsg(m!!.id,m.uid)
    }

}