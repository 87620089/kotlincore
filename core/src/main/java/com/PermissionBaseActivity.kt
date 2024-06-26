package com

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.core.BaseActivity
import com.permissionx.guolindev.callback.ExplainReasonCallback
import com.permissionx.guolindev.callback.ForwardToSettingsCallback

/**
 *  @ProjectName: core2024
 *  @Package: com
 *  @Author: lu
 *  @CreateDate: 2024/2/23 9:45
 *  @Des:权限请求页面
 */
abstract class PermissionBaseActivity<VM : ViewModel, SV : ViewDataBinding> :
    BaseActivity<VM, SV>() {

    /**
     * 具体的权限请求
     *
     */
    abstract fun permission()

    /**
     * 设置弹窗的文本
     *
     */
    abstract fun setPermissionTexts()


    protected var permissionFlag = false

    private val forwardToSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }
    protected lateinit var permissionTitle: String
    protected lateinit var permissionContent: String
    protected lateinit var permissionSure: String
    protected lateinit var permissionCancel: String


    protected lateinit var permissionRefuseTitle: String
    protected lateinit var permissionRefuseContent: String
    protected lateinit var permissionRefuseSure: String
    protected lateinit var permissionRefuseCancel: String

    protected val permissionCallBack =
        ExplainReasonCallback { _, _ ->
            setPermissionTexts()
            val build = AlertDialog.Builder(instance)
            build.setCancelable(true).setTitle(permissionTitle)
                .setMessage(permissionContent)
                .setOnCancelListener {

                }.setPositiveButton(permissionSure) { _, _ -> permission() }
                .setNegativeButton(permissionCancel) { _, _ -> }.show()
        }

    protected val permissionRefuseCallBack =
        ForwardToSettingsCallback { _, _ ->
            setPermissionTexts()
            val build = AlertDialog.Builder(instance)
            build.setCancelable(true).setTitle(permissionRefuseTitle)
                .setMessage(permissionRefuseContent)
                .setOnCancelListener {

                }.setPositiveButton(
                    permissionRefuseSure
                ) { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    forwardToSettingsLauncher.launch(intent)
                }.setNegativeButton(permissionRefuseCancel) { _, _ -> }.show()

            //            scope.showForwardToSettingsDialog(deniedList, getString(R.string.str_permiss), getString(R.string.str_permiss_sure), getString(R.string.str_update_cancel))
        }


}