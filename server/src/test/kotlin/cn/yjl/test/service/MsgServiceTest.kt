package cn.yjl.test.service

import cn.yjl.service.MsgService
import cn.yjl.service.impl.MsgServiceImpl
import cn.yjl.ssmweb.SsmWebApplication
import cn.yjl.test.annotation.SSMTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 *
 * @author YJL
 */
@SSMTest([SsmWebApplication::class])
class MsgServiceTest  {

}