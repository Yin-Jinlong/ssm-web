package cn.yjl.ssmweb

import cn.yjl.io.MemFileManager
import cn.yjl.log.util.getLogger
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
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
            p.ifEmpty { "index.html" }.let {
                if (it.endsWith("/")) {
                    resp.status = SC_FORBIDDEN
                    req.log(SC_FORBIDDEN)
                    return
                }
                it
            }
        }
        val acceptEncoding: String? = req.getHeader(HttpHeaders.ACCEPT_ENCODING)
        if (acceptEncoding != null && acceptEncoding.contains("gzip")) {
            val gz = memFileManager["$path.gz"]
            gz?.apply {
                resp.status = SC_OK
                resp.addHeader(HttpHeaders.CONTENT_ENCODING, "gzip")
                resp.setHeader(HttpHeaders.CONTENT_TYPE, req.servletContext.getMimeType(path))
                req.log(this.filePath)
                writeTo(resp)
                return
            }
        }
        memFileManager[path]?.apply {
            resp.status = SC_OK
            resp.setHeader(HttpHeaders.CONTENT_TYPE, req.servletContext.getMimeType(path))
            writeTo(resp)
            req.log(this.filePath)
            return
        }

        resp.status = SC_NOT_FOUND
        req.log(SC_NOT_FOUND)
    }
}

fun main(args: Array<String>) {
    runApplication<SsmWebApplication>(*args)
}
