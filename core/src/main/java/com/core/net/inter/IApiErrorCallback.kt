package com.core.net.inter

import com.blankj.utilcode.util.ToastUtils

/**
 *  @ProjectName: core2024
 *  @Package: com.core.net.inter
 *  @Author: lu
 *  @CreateDate: 2024/2/19 14:36
 *  @Des:
 */
interface IApiErrorCallback {
    fun onError(code: Int, error: String?) {
        ToastUtils.showShort(error)
    }
}