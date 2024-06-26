package com.core

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils

/**
 *  @ProjectName: core2024
 *  @Package: com.core
 *  @Author: lu
 *  @CreateDate: 2024/2/21 10:58
 *  @Des:
 */
abstract class BackPressedTwiceActivity<VM : ViewModel, SV : ViewDataBinding> :
    BaseActivity<VM, SV>() {
    private val WAIT_TIME = 2000L
    private var touchTime = 0L
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (System.currentTimeMillis() - touchTime < WAIT_TIME) {
            ActivityUtils.finishAllActivities()
        } else {
            touchTime = System.currentTimeMillis()
            ToastUtils.showShort(getString(R.string.str_exit_tip))
        }
    }
}