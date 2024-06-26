package com.eningqu.core2024

import com.core.bean.ApiBaseBean
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *  @ProjectName: core2024
 *  @Package: com.eningqu.core2024
 *  @Author: lu
 *  @CreateDate: 2024/2/23 11:32
 *  @Des:
 */
interface Api {
    /**
     * 鉴权测试
     */
    @POST("/app-api/ble/v2/status")
    suspend fun check(@Query("imei") imei:String,
              @Query("mac") mac:String,
              @Query("osType") osType:String,
              @Query("pkgName") pkgName:String): ApiBaseBean<CheckResult>
}