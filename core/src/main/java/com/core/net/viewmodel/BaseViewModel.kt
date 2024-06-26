package com.core.net.viewmodel

import androidx.lifecycle.ViewModel
import com.core.bean.ApiBaseBean
import com.core.net.exception.ApiException
import com.core.net.exception.ExceptionHandle
import com.core.net.inter.IApiErrorCallback
import com.core.util.EasyLog
import kotlinx.coroutines.*

/**
 *  @ProjectName: core2024
 *  @Package: com.core.net.viewmodel
 *  @Author: lu
 *  @CreateDate: 2024/2/19 15:26
 *  @Des:
 */
open class BaseViewModel : ViewModel() {
    /**
     * 运行在主线程中
     *
     * @param errorBlock
     * @param responseBlock
     */
    fun runOnUI(errorBlock: (Int?, String?) -> Unit, responseBlock: suspend () -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            safeApiCall(errorBlock = errorBlock, responseBlock)
        }
    }


    private suspend fun <T> safeApiCall(
        errorBlock: suspend (Int?, String?) -> Unit,
        responseBlock: suspend () -> T?
    ): T? {
        try {
            return responseBlock()
        } catch (e: Exception) {
            e.printStackTrace()
            val exception = ExceptionHandle.handleException(e)
            EasyLog.DEFAULT.e(exception.message)
            errorBlock(exception.code, exception.message)
        }
        return null
    }

    /**
     * 不依赖repository
     *
     * @param T
     * @param responseBlock
     * @param errorCall
     * @param successBlock
     */
    fun <T> runOnUIWithResult(
        responseBlock: suspend () -> ApiBaseBean<T>?,
        errorCall: IApiErrorCallback?,
        successBlock: (T?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = safeApiCallWithResult(errorCall = errorCall, responseBlock)
            successBlock(result)
        }
    }


    private suspend fun <T> safeApiCallWithResult(
        errorCall: IApiErrorCallback?,
        responseBlock: suspend () -> ApiBaseBean<T>?
    ): T? {
        try {
            val response = withContext(Dispatchers.IO) {
                withTimeout(10 * 1000) {
                    responseBlock()
                }
            } ?: return null

            if (response.isFailed()) {
                throw ApiException(response.code, response.msg)
                errorCall?.onError(response.code, response.msg)
            }
            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            EasyLog.DEFAULT.e(e)
            val exception = ExceptionHandle.handleException(e)
            errorCall?.onError(exception.code, exception.message)
        }
        return null
    }
}