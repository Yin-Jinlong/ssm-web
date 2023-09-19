package cn.yjl.resp

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