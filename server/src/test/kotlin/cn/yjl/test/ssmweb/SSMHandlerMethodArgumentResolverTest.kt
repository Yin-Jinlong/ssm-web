package cn.yjl.test.ssmweb

import cn.yjl.ssmweb.SSMHandlerMethodArgumentResolver
import cn.yjl.test.util.sameAs
import cn.yjl.util.now
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Timestamp
import kotlin.math.abs
import kotlin.reflect.KClass

/**
 *
 * @author YJL
 */
class SSMHandlerMethodArgumentResolverTest : SSMHandlerMethodArgumentResolver() {

    private val not = "abc??**"

    /**
     * 断言参数解析返回null
     */
    private fun KClass<*>.assertParamNot(v: String?) = parseParam(v) sameAs null

    /**
     * 断言[not]解析null
     */
    private fun KClass<*>.assertNot() = parseParam(not) sameAs null


    @Test
    fun testStringType() {
        val str = "str"

        with(String::class) {
            parseParam(str) sameAs str
            parseParam(null) sameAs null
        }
    }

    @Test
    fun testBooleanType() {
        val b1 = true
        val b1Str1 = "true"
        val b1Str2 = "1"
        val b1StrNot = "11"

        val b2 = false
        val b2Str1 = "false"
        val b2Str2 = "0"
        val b2StrNot = "-1"

        with(Boolean::class) {
            parseParam(b1Str1) sameAs b1
            parseParam(b1Str2) sameAs b1
            assertParamNot(b1StrNot)
            parseParam(b2Str1) sameAs b2
            parseParam(b2Str2) sameAs b2
            assertParamNot(b2StrNot)
            assertNot()
        }
    }

    @Test
    fun testByteType() {
        val b: Byte = 23
        val bStr = "23"
        val bStrNot = "444"

        with(Byte::class) {
            parseParam(bStr) sameAs b
            parseParam(bStr) sameAs b
            assertParamNot(bStrNot)
            assertNot()
        }
    }

    @Test
    fun testUByteType() {
        val b1: UByte = 23u
        val b2: UByte = 223u
        val b1Str = "23"
        val b2Str = "223"
        val bStrNot1 = "444"

        with(UByte::class) {
            parseParam(b1Str) sameAs b1
            parseParam(b2Str) sameAs b2
            assertParamNot(bStrNot1)
            assertNot()
        }
    }

    @Test
    fun testCharType() {
        val c = 'c'
        val cStr = "c"

        with(Char::class) {
            parseParam(cStr) sameAs c
            assertNot()
        }
    }

    @Test
    fun testIntType() {
        val int = 123456789
        val intStr = "123456789"

        with(Int::class) {
            parseParam(intStr) sameAs int
            assertNot()
        }
    }

    @Test
    fun testShort() {
        val s: Short = 666
        val sStr = "666"
        val sStrNot = "66666666"

        with(Short::class) {
            parseParam(sStr) sameAs s
            assertParamNot(sStrNot)
            assertNot()
        }
    }

    @Test
    fun testUIntType() {
        val int = 2147483648u
        val intStr = "2147483648"

        with(UInt::class) {
            parseParam(intStr) sameAs int
            assertNot()
        }
    }

    @Test
    fun testLongType() {
        val long = 1235678901234567890L
        val longStr = "1235678901234567890"

        with(Long::class) {
            parseParam(longStr) sameAs long
            assertNot()
        }
    }

    @Test
    fun testULongType() {
        val long = 9223372036854775808UL
        val longStr = "9223372036854775808"

        with(ULong::class) {
            parseParam(longStr) sameAs long
            assertNot()
        }
    }

    @Test
    fun testFloatType() {
        val f = 1.23f
        val fStr = "1.23"

        with(Float::class) {
            val pf = parseParam(fStr) as Float
            assert(abs(f - pf) < 0.01)
            assertNot()
        }
    }

    @Test
    fun testDoubleType() {
        val d = 1.2345f
        val dStr = "1.2345"

        with(Double::class) {
            val pd = parseParam(dStr) as Double
            assert(abs(d - pd) < 0.0001)
            assertNot()
            assertNot()
        }
    }

    @Test
    fun testBigIntegerType() {
        val biStr = "123456789012345678901234567890"
        val bi = BigInteger(biStr)

        with(BigInteger::class) {
            parseParam(biStr) sameAs bi
            assertNot()
        }
    }

    @Test
    fun testBigDecimalType() {
        val biStr = "1.23456789012345678901234567890"
        val bi = BigDecimal(biStr)

        with(BigDecimal::class) {
            parseParam(biStr) sameAs bi
            assertNot()
        }
    }

    @Test
    fun testTimestampType() {
        val now = now()
        val ts = Timestamp(now)
        val tsStr = now.toString()

        with(Timestamp::class) {
            parseParam(tsStr) sameAs ts
            assertNot()
        }

    }
}