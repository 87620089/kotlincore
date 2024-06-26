package com.eningqu.core2024


import com.core.net.repository.BaseRepository

/**
 *  @ProjectName: core2024
 *  @Package: com.eningqu.core2024
 *  @Author: lu
 *  @CreateDate: 2024/2/23 11:40
 *  @Des:
 */
class CheckRepository: BaseRepository() {

    suspend fun check(imei: String, mac:  String,osType:String,pkgName:String): CheckResult? {
        return requestResponseWithRetry {
            ApiManager.api.check(imei,mac,osType,pkgName)
        }
    }
}