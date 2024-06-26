package com.core.view

import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.core.R
import java.util.*

/**
 *  @ProjectName: core2024
 *  @Package: com.core.view
 *  @Author: lu
 *  @CreateDate: 2024/2/21 11:31
 *  @Des:
 */
abstract class PerfectClickListener : View.OnClickListener {
    private var lastClickTime: Long = 0
    private var id = -1
    private val MIN_CLICK_DELAY_TIME = 1000
    override fun onClick(v: View) {
        val currentTime = Calendar.getInstance().timeInMillis
        val mId = v.id
        if (id != mId) {
            id = mId
            lastClickTime = currentTime
            onNoDoubleClick(v)
            return
        }
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
        } else {
            ToastUtils.showShort(R.string.str_double_click_tip)
        }
    }

    protected abstract fun onNoDoubleClick(v: View)
}