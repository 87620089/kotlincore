package com.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.bean.EventBusCarrier
import com.core.util.ClassUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *  @ProjectName: core2024
 *  @Package: com.core
 *  @Author: lu
 *  @CreateDate: 2024/2/21 11:24
 *  @Des:
 */
abstract class BaseFragment<VM : ViewModel, SV : ViewDataBinding> : Fragment() {
    protected var regEvent: Boolean = false
    protected var activity: Activity? = null

    // 布局view
    protected lateinit var mViewBinding: SV

    // ViewModel
    protected lateinit var mViewModel: VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getLayoutId() != 0) {
            mViewBinding = DataBindingUtil.inflate(
                requireActivity().layoutInflater,
                getLayoutId(),
                null,
                false
            )
        }

        initEventBus()
        initViewModel()

        initData()
        initView(savedInstanceState)

    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化views
     *
     * @param state
     */
    protected abstract fun initView(state: Bundle?)

    /**
     * 初始化数据
     */
    protected abstract fun initData()

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

    protected open fun onEvent(event: EventBusCarrier) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as? FragmentActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (regEvent) {
            EventBus.getDefault().unregister(this)
        }
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this)[ClassUtil.getVmClazz(this)]
    }

    @JvmOverloads
    fun startActivity(clz: Class<*>?, bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(requireActivity(), clz!!)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    @JvmOverloads
    fun startActivityForResult(cls: Class<*>?, bundle: Bundle?, requestCode: Int) {
        val intent = Intent()
        intent.setClass(requireActivity(), cls!!)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

}