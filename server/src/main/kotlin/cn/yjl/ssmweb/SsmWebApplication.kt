package cn.yjl.ssmweb

import cn.yjl.io.MemFile
import cn.yjl.io.MemFileManager
import cn.yjl.log.util.getLogger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication(scanBasePackages = ["cn.yjl.ssmweb"])
class SsmWebApplication {

    @Autowired
    lateinit var memFileManager: MemFileManager

    val log = getLogger()

    private fun HttpServletRequest.log(res: Any) =
        log.info("GET $requestURI -> $res")

    @RequestMapping("/**")
    fun static(req: HttpServletRequest, resp: HttpServletResponse) {
        val path = run {
            val p = req.requestURI.substring(1)
            p.ifEmpty { "index.html" }.apply {
                if (endsWith("/")) {
                    resp.status = SC_FORBIDDEN
                    req.log(SC_FORBIDDEN)
                    return
                }
            }
        }
        path.openMemFileForEncoding(req, resp)?.apply {
            resp.status = SC_OK
            resp.setHeader(HttpHeaders.CONTENT_TYPE, req.servletContext.getMimeType(path))
            writeTo(resp)
            req.log(filePath)
            return
        }
        resp.status = SC_NOT_FOUND
        req.log(SC_NOT_FOUND)
    }

    /**
     * 打开请求的文件，如果gz存在则返回gz文件
     */
    private fun String.openMemFileForEncoding(req: HttpServletRequest, resp: HttpServletResponse): MemFile? {
        if (req.getHeader(HttpHeaders.ACCEPT_ENCODING)?.contains("gzip") == true) {
            memFileManager["$this.gz"]?.apply {
                resp.addHeader(HttpHeaders.CONTENT_ENCODING, "gzip")
                return this
            }
        }
        return memFileManager[this]
    }
}

fun main(args: Array<String>) {
    runApplication<SsmWebApplication>(*args)
}