package com.core.net.repository

import com.blankj.utilcode.util.ToastUtils
import com.core.app.AppConfig
import com.core.app.ConfigKeys
import com.core.bean.ApiBaseBean
import com.core.net.exception.ApiException
import com.core.util.EasyLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.IOException

/**
 *  @ProjectName: core2024
 *  @Package: com.core.net.repository
 *  @Author: lu
 *  @CreateDate: 2024/2/19 15:59
 *  @Des:
 */
open class BaseRepository {

    /**
     * io请求
     *
     * @param T
     * @param requestCall
     * @return
     */
    private suspend fun <T> requestResponse(requestCall: suspend () -> ApiBaseBean<T>?): T? {
        val response = withContext(Dispatchers.IO) {
            withTimeout((AppConfig.getConfiguration(ConfigKeys.TIME_OUT) as Int) * 1000L) {
                requestCall()
            }
        } ?: return null

        if (response.isFailed()) {
            ToastUtils.showLong(response.msg)
//            EasyLog.DEFAULT.e(response)
//            throw ApiException(response.code, response.msg)
        }
        return response.data
    }


    /**
     * 带重试机制的请求
     *
     * @param T
     * @param requestCall
     * @return
     */
    suspend fun <T> requestResponseWithRetry(
        requestCall: suspend () -> ApiBaseBean<T>?
    ): T? {
        var currentRetry = 0
        while (currentRetry < AppConfig.getConfiguration(ConfigKeys.RETRY_COUNT) as Int) {
            try {
                return requestResponse(requestCall)
            } catch (e: IOException) {
                // 网络错误重试
                currentRetry++
                if (currentRetry == AppConfig.getConfiguration(ConfigKeys.RETRY_COUNT) as Int) {
                    throw e
                }else{
                    delay(AppConfig.getConfiguration(ConfigKeys.RETRY_TIME) as Int * 1000L)
                }
            } catch (e: ApiException) {
                // 处理 API 错误
                throw e
                delay(AppConfig.getConfiguration(ConfigKeys.RETRY_TIME) as Int * 1000L)
            } catch (e: Exception) {
                // 处理其他错误
                throw e
                delay(AppConfig.getConfiguration(ConfigKeys.RETRY_TIME) as Int * 1000L)
            }

        }
        return null
    }
}