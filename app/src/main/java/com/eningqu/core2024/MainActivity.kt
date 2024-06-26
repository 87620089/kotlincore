package com.eningqu.core2024

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.window.core.layout.WindowSizeClass
import androidx.window.layout.WindowMetricsCalculator
import com.PermissionBaseActivity
import com.blankj.utilcode.util.ToastUtils
import com.core.net.RetrofitClient
import com.core.util.EasyLog
import com.core.util.NetworkUtil
import com.eningqu.core2024.databinding.ActivityMainBinding
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.*


class MainActivity : PermissionBaseActivity<TestViewModel
        , ActivityMainBinding>() {
    override fun permission() {
        PermissionX.init(this).permissions(
            Manifest.permission.RECORD_AUDIO,

            ).onExplainRequestReason(permissionCallBack).onForwardToSettings(permissionRefuseCallBack)
            .request { allGranted, _, _ ->
                if (allGranted) {
                    showLoading( isCancelable = true, isCancelOutside = true)
//                    CoroutineScope(Dispatchers.Main+ SupervisorJob()).launch {
//                        withContext(Dispatchers.IO) {
//                            val result=RetrofitClient.createApi(Api::class.java).check(
//                                "",
//                                "02:12:34:03:93:B4",
//                                "2",
//                                "com.eningqu.speakfreely"
//                            )
//                        }
                    mViewModel.check("","02:12:34:03:93:B4","2","com.eningqu.speakfreely")
                }
            }
    }

    override fun setPermissionTexts() {
        permissionTitle="title1"
        permissionContent="content1"
        permissionSure="sure"
        permissionCancel="cancel"
        permissionRefuseTitle="title2"
        permissionRefuseContent="content2"
        permissionRefuseSure="sure2"
        permissionRefuseCancel="cancel2"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        mViewModel.checkLiveData.observe(this){
            dismissLoading()
            if(it==null){
                return@observe
            }
            EasyLog.DEFAULT.e(it)

            process=0F
            job?.cancel(null)
            mViewBinding.circleDownloadProgress.visibility=View.VISIBLE
            testCircleDownload()
        }

        EasyLog.DEFAULT.e(NetworkUtil.isConnected(this))

        mViewBinding.circleDownloadProgress.setOnClickListener {
            mViewBinding.circleDownloadProgress.currentProcess=0F
            mViewBinding.circleDownloadProgress.visibility=View.GONE
            job?.cancel(null)
        }
    }

    override fun initView(state: Bundle?) {
        mViewBinding.btn.setOnClickListener {
//            showLoading( isCancelable = true, isCancelOutside = true)
            permission()
        }
    }


    /**
     * 测试
     *
     */
    var process=0F
    private var job: Job? = null
     private  fun testCircleDownload(){
        job=GlobalScope.launch(Dispatchers.IO) {
            delay(1000L)
            mViewBinding.circleDownloadProgress.currentProcess= process
            mViewBinding.circleDownloadProgress.postInvalidate()
            process++
//            EasyLog.DEFAULT.e(process)
            if(process<=100) {
                testCircleDownload()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        computeWindowSizeClasses()
    }

    private fun computeWindowSizeClasses() {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        val density = resources.displayMetrics.density

        EasyLog.DEFAULT.e(width/density)

        val windowSizeClass = WindowSizeClass.compute(width/density, height/density)
        // COMPACT, MEDIUM, or EXPANDED
        val widthWindowSizeClass = windowSizeClass.windowWidthSizeClass
        // COMPACT, MEDIUM, or EXPANDED
        val heightWindowSizeClass = windowSizeClass.windowHeightSizeClass

        EasyLog.DEFAULT.e(widthWindowSizeClass)

        EasyLog.DEFAULT.e(heightWindowSizeClass)

        ToastUtils.showShort(widthWindowSizeClass.toString())
        // Use widthWindowSizeClass and heightWindowSizeClass.
    }

}