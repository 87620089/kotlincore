package com.core

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.core.bean.EventBusCarrier
import com.core.view.dialog.LoadingDialog
import com.core.util.ClassUtil
import com.core.util.StatusBarUtil
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *  @ProjectName: core2024
 *  @Package: com.core
 *  @Author: lu
 *  @CreateDate: 2024/2/19 11:00
 *  @Des:
 */
abstract class BaseActivity<VM : ViewModel, SV : ViewDataBinding> : AppCompatActivity() {
    protected val instance by lazy { this }

    // 布局view
    protected lateinit var mViewBinding: SV
    protected lateinit var mViewModel: VM

    //eventbus
    protected var regEvent = false

    //禁止屏幕录制
    protected var screenSecure = false

    //常亮
    protected var screenOn = false

    //弹窗
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getLayoutId() != 0) {
            mViewBinding = DataBindingUtil.setContentView(this, getLayoutId())
        }
        initEventBus()
        initViewModel()

        initData()
        initView(savedInstanceState)

        if (screenSecure) {
            //禁止app录屏和截屏
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
        if (screenOn) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    //加载布局
    protected abstract fun getLayoutId(): Int

    //加载数据
    protected abstract fun initData()

    //加载事件
    protected abstract fun initView(state: Bundle?)

    override fun onStop() {
        super.onStop()
        ToastUtils.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (regEvent) {
            EventBus.getDefault().unregister(this)
        }
    }

    /**
     * 初始化EventBus
     */
    private fun initEventBus() {
        if (regEvent) {
            EventBus.getDefault().register(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onMessageEvent(event: EventBusCarrier) {
        onEvent(event)
    }

    protected open fun onEvent(event: EventBusCarrier) {}

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this)[ClassUtil.getVmClazz(this)]
    }

    @JvmOverloads
    fun startActivity(clz: Class<*>?, bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(this.applicationContext, clz!!)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    @JvmOverloads
    fun startActivityForResult(cls: Class<*>?, bundle: Bundle?, requestCode: Int) {
        val intent = Intent()
        intent.setClass(this.applicationContext, cls!!)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }


    /**
     * 此处设置沉浸式地方
     */
    protected fun setStatusBar(
        @ColorInt colorInt: Int,
        @IntRange(from = 0, to = 255) alphaInt: Int = 80
    ) {
        StatusBarUtil.setTranslucent(this, alphaInt)
        StatusBarUtil.setColor(this, colorInt)
    }

    @JvmOverloads
    protected fun showLoading(
        msg: String = getString(R.string.str_loading),
        isCancelable: Boolean = false,
        isCancelOutside: Boolean = false
    ) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.Builder().setMessage(msg).setCancelable(isCancelable)
                .setCancelOutside(isCancelOutside)
                .create()
        }
        loadingDialog?.show(supportFragmentManager, "loading")
    }

    protected fun dismissLoading() {

        CoroutineScope(Dispatchers.IO).launch {
            delay(1000L)
            withContext(Dispatchers.Main) {
                loadingDialog?.dismiss()
            }
        }

    }
}