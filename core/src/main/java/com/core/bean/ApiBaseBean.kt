package com.core.bean

import java.io.Serializable

/**
 *  @ProjectName: core2024
 *  @Package: com.core.bean
 *  @Author: lu
 *  @CreateDate: 2024/2/19 16:03
 *  @Des:
 */
class ApiBaseBean<T>(
    val msg: String? = null,
    val code: Int = 0,
    val isSuccess: Boolean = false,
    val data: T? = null
) : Serializable {

    /**
     * 判定接口返回是否正常
     */
    fun isFailed(): Boolean {
        return code != 0
    }
}