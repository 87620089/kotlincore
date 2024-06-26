package com.core.net.exception

import android.net.ParseException
import retrofit2.HttpException
import android.util.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import javax.net.ssl.SSLException
import com.google.gson.JsonParseException

/**
 *  @ProjectName: core2024
 *  @Package: com.core.net.exception
 *  @Author: lu
 *  @CreateDate: 2024/2/19 15:42
 *  @Des:
 */
class ExceptionHandle {

    companion object {
        private val UNAUTHORIZED = 401
        private val FORBIDDEN = 403
        private val NOT_FOUND = 404
        private val REQUEST_TIMEOUT = 408
        private val INTERNAL_SERVER_ERROR = 500
        private val SERVICE_UNAVAILABLE = 503

        fun handleException(e: Throwable?): ErrorMsg {
            val errorMsg: ErrorMsg
            return when (e) {
                is HttpException -> {
                    errorMsg = ErrorMsg(e, ERROR.HTTP_ERROR)
                    when (e.code()) {
                        UNAUTHORIZED -> errorMsg.message = "操作未授权"
                        FORBIDDEN -> errorMsg.message = "请求被拒绝"
                        NOT_FOUND -> errorMsg.message = "资源不存在"
                        REQUEST_TIMEOUT -> errorMsg.message = "服务器执行超时"
                        INTERNAL_SERVER_ERROR -> errorMsg.message = "服务器内部错误"
                        SERVICE_UNAVAILABLE -> errorMsg.message = "服务器不可用"
                        else -> errorMsg.message = "网络异常"
                    }
                    errorMsg
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    errorMsg = ErrorMsg(e, ERROR.PARSE_ERROR)
                    errorMsg.message = "parse error"
                    errorMsg
                }
                is SSLException -> {
                    errorMsg = ErrorMsg(e, ERROR.SSL_ERROR)
                    errorMsg.message = "证书验证失败"
                    errorMsg
                }
                is ConnectTimeoutException -> {
                    errorMsg = ErrorMsg(e, ERROR.TIMEOUT_ERROR)
                    errorMsg.message = "连接超时"
                    errorMsg
                }
                is SocketTimeoutException -> {
                    errorMsg = ErrorMsg(e, ERROR.TIMEOUT_ERROR)
                    errorMsg.message = "连接超时"
                    errorMsg
                }
                is UnknownHostException -> {
                    errorMsg = ErrorMsg(e, ERROR.TIMEOUT_ERROR)
                    errorMsg.message = "网络异常"
                    errorMsg
                }
                is UnknownServiceException -> {
                    errorMsg = ErrorMsg(e, ERROR.NETWORD_ERROR)
                    errorMsg.message ="网络异常"
                    errorMsg
                }
                is ConnectException -> {
                    errorMsg = ErrorMsg(e, ERROR.NETWORD_ERROR)
                    errorMsg.message = "网络异常"
                    errorMsg
                }
                else -> {
                    errorMsg = ErrorMsg(e, ERROR.UNKNOWN)
                    errorMsg.message = "未知错误"
                    errorMsg
                }
            }
        }
    }

    /**
     * 约定异常 这个具体规则需要与服务端或者领导商讨定义
     */
    internal object ERROR {
        /**
         * 未知错误
         */
        const val UNKNOWN = 1000

        /**
         * 解析错误
         */
        const val PARSE_ERROR = 1001

        /**
         * 网络错误
         */
        const val NETWORD_ERROR = 1002

        /**
         * 协议出错
         */
        const val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        const val SSL_ERROR = 1005

        /**
         * 连接超时
         */
        const val TIMEOUT_ERROR = 1006
    }
}