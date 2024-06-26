package com.core.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.ToastUtils
import com.core.R

/**
 *  @ProjectName: core2024
 *  @Package: com.core.util
 *  @Author: lu
 *  @CreateDate: 2024/2/20 9:44
 *  @Des:
 */
class IntentReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if(NetworkUtil.isConnected(p0!!)){
            ToastUtils.showShort(R.string.str_net_connected)
        }else{
            ToastUtils.showShort(R.string.str_net_not_connected)
        }
    }
}