package com.eningqu.core2024

import com.blankj.utilcode.util.ToastUtils
import com.core.BaseApplication
import com.core.app.AppConfig

/**
 *  @ProjectName: core2024
 *  @Package: com.eningqu.core2024
 *  @Author: lu
 *  @CreateDate: 2024/2/21 17:59
 *  @Des:
 */
class Application :BaseApplication(){

    protected override fun onNetAvailable(){
        ToastUtils.showShort("网络链接成功！")
    }

    override fun onNetLost() {
        ToastUtils.showShort("网络断开链接！")
    }

    override fun onCreate() {
        super.onCreate()
        AppConfig.init().withApiHost("https://sfp.eningqu.com")
            .withRetryCount(2).withTimeOut(3).withRetryTime(3).configure()
    }
}