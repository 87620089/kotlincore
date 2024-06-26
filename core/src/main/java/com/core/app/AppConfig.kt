package com.core.app

import android.os.Handler

/**
 *  @ProjectName: core2024
 *  @Package: com.core.app
 *  @Author: lu
 *  @CreateDate: 2024/2/19 17:30
 *  @Des:
 */
object AppConfig {
    /**
     * 初始化配置上下文
     * @return
     */
    fun init(): Configurator {
        return Configurator.sIntance
    }

    /**
     * 根据key获取配置信息
     * @param key
     * @param <T>
     * @return
    </T> */
    fun <T> getConfiguration(key: Any?): T {
        return Configurator.sIntance.getConfiguration(key!!)!!
    }
}