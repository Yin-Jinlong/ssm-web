package cn.yjl.ssmweb

import com.google.gson.Gson
import org.junit.jupiter.api.Test

class GsonTests {

    /**
     * 测试Gson是否正常
     */
    @Test
    fun testGson() {
        val gson = Gson()
        val gt=gson.fromJson("{name:'gson',age:20}",GsonTestClass::class.java)
        assert(gt.name=="gson")
        assert(gt.age==20)
    }

}

class GsonTestClass {
    var name: String? = null
    var age: Int = 18
}