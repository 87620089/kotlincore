package com.eningqu.core2024

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.core.net.viewmodel.BaseViewModel
import com.core.util.EasyLog
import com.core.view.dialog.LoadingDialog

/**
 *  @ProjectName: core2024
 *  @Package: com.eningqu.core2024
 *  @Author: lu
 *  @CreateDate: 2024/2/23 10:54
 *  @Des:
 */
class TestViewModel:BaseViewModel() {
    val checkLiveData = MutableLiveData<CheckResult?>()
    private val checkRepository by lazy { CheckRepository() }


    fun check(imei: String, mac:  String,osType:String,pkgName:String): LiveData<CheckResult?> {
        runOnUI(errorBlock = { code, error ->
//            EasyLog.DEFAULT.e(error)
            ToastUtils.showShort(error)
            checkLiveData.value = null
        }) {
            val data = checkRepository.check(imei, mac,osType,pkgName)
            checkLiveData.value = data
        }
        return checkLiveData
    }
}