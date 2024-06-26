package com.core.util

import java.lang.reflect.ParameterizedType

/**
 *  @ProjectName: core2024
 *  @Package: com.core.util
 *  @Author: lu
 *  @CreateDate: 2024/2/19 11:48
 *  @Des:
 */
object ClassUtil {
    /**
     * 获取当前类绑定的泛型ViewModel-clazz
     */
    @Suppress("UNCHECKED_CAST")
    fun <VM> getVmClazz(obj: Any): VM {
        return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }
}