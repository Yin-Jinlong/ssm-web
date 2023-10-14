package cn.yjl.util.test.log

import cn.yjl.test.util.sameAs
import cn.yjl.util.log.getLogger
import org.junit.jupiter.api.Test

class LoggerUtilTest {

    private val LOGGER = getLogger()

    @Test
    fun testLoggerName() {
        LOGGER.name sameAs this::class.java.simpleName
    }

}