package com.core.app

import com.core.util.EasyLog
import okhttp3.Interceptor

/**
 *  @ProjectName: core2024
 *  @Package: com.core.app
 *  @Author: lu
 *  @CreateDate: 2024/2/19 17:07
 *  @Des:
 */
 class Configurator {
    companion object {
        /**
         * 获取配置容器
         *
         * @return
         */
        /**
         * 用于存储各个配置
         */
        val coreConfigs = HashMap<Any, Any>()

        /**
         * 拦截器存储管理器
         */
        private val INTERCEPTORS = ArrayList<Interceptor>()

        val sIntance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Configurator()
        }
    }


    /**
     * 获取某个key的配置值
     *
     * @param key
     * @param <T>
     * @return
    </T> */
    fun <T> getConfiguration(key: Any): T? {
        return coreConfigs[key] as T?
    }


    /**
     * 配置host地址
     *
     * @param host
     * @return
     */
    fun withApiHost(host: String): Configurator {
        coreConfigs[ConfigKeys.API_HOST] = host
        return this
    }

    /**
     * 间隔 单位秒
     *
     * @param retryTime
     * @return
     */
    fun withRetryTime(retryTime: Int): Configurator {
        coreConfigs[ConfigKeys.RETRY_TIME] = retryTime
        return this
    }

    /**
     * 次数
     *
     * @param retryCount
     * @return
     */
    fun withRetryCount(retryCount: Int): Configurator {
        coreConfigs[ConfigKeys.RETRY_COUNT] = retryCount
        return this
    }

    /**
     * 超时时间单位秒
     *
     * @param timeOut
     * @return
     */
    fun withTimeOut(timeOut:Int=10):Configurator{
        coreConfigs[ConfigKeys.TIME_OUT] = timeOut
        return this
    }

    /**
     * 增加一个拦截器
     *
     * @param interceptor
     * @return
     */
    fun withInterceptor(interceptor: Interceptor): Configurator {
        INTERCEPTORS.add(interceptor)
        coreConfigs[ConfigKeys.INTERCEPTOR] = INTERCEPTORS
        return this
    }

    fun configure() {
       EasyLog.DEFAULT.i("基本信息加载完成！")
    }

    /**
     * 创建对象时，初始化为未配置状态
     */
    init {
        coreConfigs[ConfigKeys.INTERCEPTOR] = INTERCEPTORS
    }
}