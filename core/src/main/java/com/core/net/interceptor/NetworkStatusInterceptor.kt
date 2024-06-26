package com.core.net.interceptor

import com.core.BaseApplication
import com.core.R
import com.core.bean.EventBusCarrier
import com.core.constant.CoreConstant
import com.core.net.exception.ApiException
import com.core.net.exception.ExceptionHandle
import com.core.util.NetworkUtil
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.greenrobot.eventbus.EventBus
import java.io.IOException

/**
 *  @ProjectName: core2024
 *  @Package: com.core.net.interceptor
 *  @Author: lu
 *  @CreateDate: 2024/2/20 9:26
 *  @Des:
 */
class NetworkStatusInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (NetworkUtil.isConnected(BaseApplication.getInstance())) {
            val request = chain.request()

            return chain.proceed(request)
        } else {
            val responseBody = "Net Not Connected".toByteArray().toResponseBody()
            EventBus.getDefault().post(EventBusCarrier(CoreConstant.NetworkError))
            return Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1)
                .code(404) // 例如，使用 403 Forbidden 状态码
                .body(responseBody)
                .message(BaseApplication.getInstance().getString(R.string.str_net_not_connected))
                .build()
        }
    }

}