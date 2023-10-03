package cn.yjl.io

import cn.yjl.util.sync
import jakarta.servlet.ServletResponse
import java.io.OutputStream
import kotlin.reflect.KProperty

/**
 * @author YJL
 */
abstract class AbstractMemFile : MemFile {

    internal var data by AtomicData()

    /**
     * 内存中修改时间
     */
    internal var memTime: Long = -1

    override val size: UInt
        get() = data.size.uint

    private fun <R> updateRun(block: () -> R): R {
        sync{
            if (lastModified != memTime) {
                update()
                memTime = lastModified
            }
        }
        return block()
    }

    override fun get(index: UInt): Byte = updateRun { data[index.int] }

    override fun get(start: UInt, end: UInt) = updateRun { data.copyOfRange(start.int, end.int) }

    override fun rangeTo(end: UInt) = updateRun { data.copyOfRange(0, end.int) }

    override fun buffer(): ByteArray = updateRun { data.clone() }

    override fun writeTo(out: OutputStream) = updateRun { out.write(data) }

    override fun writeTo(out: ServletResponse) = updateRun { out.outputStream.write(data) }

    /**
     * 更新内存文件
     */
    abstract fun update()

}

private val UInt.int
    inline get() = toInt()

private val Int.uint
    inline get() = toUInt()

internal class AtomicData(initData: ByteArray = ByteArray(0)) {

    @Volatile
    var data = initData

    operator fun getValue(memFile: MemFile, property: KProperty<*>): ByteArray {
        return data
    }

    operator fun setValue(memFile: MemFile, property: KProperty<*>, array: ByteArray) {
        data = array
    }
}