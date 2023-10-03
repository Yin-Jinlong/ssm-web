package cn.yjl.test.ssmweb

import cn.yjl.ssmweb.SsmWebApplication
import cn.yjl.test.annotation.SSMTest
import cn.yjl.test.util.sameAs
import com.google.gson.Gson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@SSMTest([SsmWebApplication::class])
class GsonTests {

    @Autowired
    lateinit var gson: Gson

    /**
     * 测试Gson是否正常
     */
    @Test
    fun testGson() {
        val gt = gson.fromJson("{name:'gson',age:20}", GsonTestClass::class.java)
        gt.name sameAs "gson"
        gt.age sameAs 20
    }

}

class GsonTestClass {
    var name: String? = null
    var age: Int = 18
}