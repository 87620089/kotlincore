package com.core.net.exception


/**
 *  @ProjectName: core2024
 *  @Package: com.core.net.exception
 *  @Author: lu
 *  @CreateDate: 2024/2/19 16:09
 *  @Des:
 */

open class  ApiException(val code: Int, val msg: String?) : Exception(msg)

