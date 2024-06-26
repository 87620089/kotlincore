package com.core.net.exception

import java.lang.Exception

/**
 *  @ProjectName: core2024
 *  @Package: com.core.net.exception
 *  @Author: lu
 *  @CreateDate: 2024/2/19 15:42
 *  @Des:
 */
class ErrorMsg(throwable: Throwable?, var code: Int) : Exception(throwable) {
    override lateinit var message: String
}