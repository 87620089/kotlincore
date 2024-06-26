package com.core.view.dialog.proxy

import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener
import java.lang.ref.WeakReference

/**
 * @ProjectName:
 * @Package: com.core.dialog
 * @Author:  原文链接：https://blog.csdn.net/u010231682/article/details/106807086
 * @CreateDate: 2022/7/15 18:06
 * @Des:
 */
class DialogInterfaceProxy {
    companion object{
        fun proxy(onCancelListener: DialogInterface.OnCancelListener?): DialogInterface.OnCancelListener {
            return ProxyOnCancelListener(onCancelListener)
        }

        fun proxy(onDismissListener: DialogInterface.OnDismissListener?): DialogInterface.OnDismissListener {
            return ProxyOnDismissListener(onDismissListener)
        }

        fun proxy(onShowListener: OnShowListener?): OnShowListener {
            return ProxyOnShowListener(onShowListener)
        }
    }


    /**
     * 使用弱引用持有Dialog真正的DialogInterface.OnCancelListener,
     *
     *
     * 即使Message.obj未被正常释放，最终它持有的也只是ProxyOnCancelListener的一个空壳实例
     */
    class ProxyOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?) :
        DialogInterface.OnCancelListener {
        private val mProxyRef: WeakReference<DialogInterface.OnCancelListener?>
        override fun onCancel(dialog: DialogInterface) {
            val onCancelListener = mProxyRef.get()
            onCancelListener?.onCancel(dialog)
        }

        init {
            mProxyRef = WeakReference(onCancelListener)
        }
    }

    /**
     * 使用弱引用持有Dialog真正的DialogInterface.OnDismissListener,
     *
     *
     * 即使Message.obj未被正常释放，最终它持有的也只是ProxyOnDismissListener的一个空壳实例
     */
    class ProxyOnDismissListener(onDismissListener: DialogInterface.OnDismissListener?) :
        DialogInterface.OnDismissListener {
        private val mProxyRef: WeakReference<DialogInterface.OnDismissListener?>
        override fun onDismiss(dialog: DialogInterface) {
            val onDismissListener = mProxyRef.get()
            onDismissListener?.onDismiss(dialog)
        }

        init {
            mProxyRef = WeakReference(onDismissListener)
        }
    }

    /**
     * 使用弱引用持有Dialog真正的DialogInterface.OnShowListener,
     *
     *
     * 即使Message.obj未被正常释放，最终它持有的也只是ProxyOnShowListener的一个空壳实例
     */
    class ProxyOnShowListener(onShowListener: OnShowListener?) : OnShowListener {
        private val mProxyRef: WeakReference<OnShowListener?>
        override fun onShow(dialog: DialogInterface) {
            val onShowListener = mProxyRef.get()
            onShowListener?.onShow(dialog)
        }

        init {
            mProxyRef = WeakReference(onShowListener)
        }
    }
}