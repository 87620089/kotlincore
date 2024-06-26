package com.eningqu.core2024

import com.core.net.RetrofitClient

/**
 * @ProjectName: core2024
 * @Package: com.eningqu.core2024
 * @Author: lu
 * @CreateDate: 2024/2/23 15:36
 * @Des:
 */
object ApiManager {
    val api by lazy { RetrofitClient.createApi(Api::class.java) }
}