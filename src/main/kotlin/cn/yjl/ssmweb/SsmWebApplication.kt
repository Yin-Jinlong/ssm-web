package cn.yjl.ssmweb

import cn.yjl.io.MemFileManager
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN
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

    @RequestMapping("/**")
    fun static(req: HttpServletRequest, resp: HttpServletResponse) {
        val path = run {
            val p = req.requestURI.substring(1)
            p.ifEmpty { "index.html" }.let {
                if (it.endsWith("/")) {
                    resp.status = SC_FORBIDDEN
                    return
                }
                it
            }
        }
        val acceptEncoding: String? = req.getHeader(HttpHeaders.ACCEPT_ENCODING)
        if (acceptEncoding != null && acceptEncoding.contains("gzip")) {
            val gz = memFileManager["$path.gz"]
            gz?.apply {
                resp.status = HttpServletResponse.SC_OK
                resp.addHeader(HttpHeaders.CONTENT_ENCODING, "gzip")
                resp.setHeader(HttpHeaders.CONTENT_TYPE, req.servletContext.getMimeType(path))
                writeTo(resp)
                return
            }
        }
        memFileManager[path]?.apply {
            resp.status = HttpServletResponse.SC_OK
            resp.setHeader(HttpHeaders.CONTENT_TYPE, req.servletContext.getMimeType(path))
            writeTo(resp)
            return
        }

        resp.status = HttpServletResponse.SC_NOT_FOUND
    }
}

fun main(args: Array<String>) {
    runApplication<SsmWebApplication>(*args)
}
