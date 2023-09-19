package cn.yjl.test.ssmweb

import com.google.gson.Gson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GsonTests {

    @Autowired
    lateinit var gson:Gson

    /**
     * 测试Gson是否正常
     */
    @Test
    fun testGson() {
        val gt=gson.fromJson("{name:'gson',age:20}", GsonTestClass::class.java)
        assert(gt.name=="gson")
        assert(gt.age==20)
    }

}

class GsonTestClass {
    var name: String? = null
    var age: Int = 18
}