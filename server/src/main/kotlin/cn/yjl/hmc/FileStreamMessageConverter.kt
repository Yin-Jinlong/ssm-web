package cn.yjl.hmc

import cn.yjl.io.FileStream
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.http.MediaTypeFactory
import org.springframework.http.converter.AbstractGenericHttpMessageConverter
import org.springframework.http.converter.HttpMessageNotWritableException
import java.lang.reflect.Type

/**
 *
 * @author YJL
 */
class FileStreamMessageConverter : AbstractGenericHttpMessageConverter<FileStream>(APPLICATION_OCTET_STREAM) {

    override fun supports(clazz: Class<*>): Boolean {
        if (clazz == FileStream::class.java)
            return true
        if (clazz == Object::class.java)
            return false
        return supports(clazz.superclass)
    }

    override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return supports(clazz)
    }

    override fun readInternal(clazz: Class<out FileStream>, inputMessage: HttpInputMessage): FileStream {
        return read(inputMessage)
    }

    override fun read(type: Type, contextClass: Class<*>?, inputMessage: HttpInputMessage): FileStream {
        return read(inputMessage)
    }

    private fun read(inputMessage: HttpInputMessage): FileStream {
        val cd = inputMessage.headers.contentDisposition
        return cd.filename?.let { FileStream(it, inputMessage.body) }
            ?: throw HttpMessageNotWritableException("No file name specified")
    }

    override fun writeInternal(f: FileStream, type: Type?, outputMessage: HttpOutputMessage) {
        val cd = ContentDisposition.builder(TEXT_PLAIN.type)
        cd.filename(f.name)

        outputMessage.headers.apply {
            if (f.download) {
                contentDisposition = cd.build()
                contentType = APPLICATION_OCTET_STREAM
            } else {
                contentType = MediaTypeFactory.getMediaType(f.name).orElse(APPLICATION_OCTET_STREAM)
            }
            contentLength = f.ins.available().toLong()
        }
        outputMessage.body.apply {
            f.ins.copyTo(this)
            flush()
        }
    }

}