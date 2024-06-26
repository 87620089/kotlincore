package com.core.view.dialog.proxy

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatDialog
import android.content.DialogInterface.OnShowListener
import com.core.view.dialog.proxy.DialogInterfaceProxy.Companion.proxy

/**
 * @ProjectName: nqmoudlepro
 * @Package: com.eningqu.core.dialog
 * @Author: 原文链接：https://blog.csdn.net/u010231682/article/details/106807086
 * @CreateDate: 2022/7/15 18:07
 * @Des:
 */
class ProxyDialog : AppCompatDialog {
    constructor(context: Context) : super(context)
    constructor(context: Context, theme: Int) : super(context, theme)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)


    override fun setOnCancelListener(listener: DialogInterface.OnCancelListener?) {
        super.setOnCancelListener(proxy(listener))
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(proxy(listener))
    }

    override fun setOnShowListener(listener: OnShowListener?) {
        super.setOnShowListener(proxy(listener))
    }
}