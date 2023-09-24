package cn.yjl.resp

/**
 * 返回的json基础模板
 *
 * 只能在该接口上扩充
 */
interface ResponseJson {
    /**
     * 响应码，多用于错误处理
     */
    var code: Int

    /**
     *  响应信息
     */
    var msg: String
}