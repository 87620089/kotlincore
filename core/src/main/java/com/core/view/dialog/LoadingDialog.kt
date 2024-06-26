package com.core.view.dialog


import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.core.R
import com.core.view.dialog.proxy.ProxyDialog


/**
 * @ProjectName: core2024
 * @Package: com.core.dialog
 * @Author: lu
 * @CreateDate: 2024/2/20 11:01
 * @Des:
 */
class LoadingDialog : DialogFragment {

    class Builder {
        private var message: String? = null
        private var isCancelable = false
        private var isCancelOutside = false

        /**
         * 设置提示信息
         * @param message
         * @return
         */
        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        /**
         * 设置是否可以按返回键取消
         * @param isCancelable
         * @return
         */
        fun setCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }

        /**
         * 设置是否可以取消
         * @param isCancelOutside
         * @return
         */
        fun setCancelOutside(isCancelOutside: Boolean): Builder {
            this.isCancelOutside = isCancelOutside
            return this
        }

        fun create(): LoadingDialog {
            return LoadingDialog(message, isCancelable, isCancelOutside)
        }
    }



//    private var rootView: View? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NO_TITLE, com.core.R.style.MyDialogStyle)
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setCancelable(isCancelable)
//        dialog?.setCancelable(isCancelable)
//        dialog?.setCanceledOnTouchOutside(isCancelOutside)
//    }

//        override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        rootView = super.onCreateView(inflater,container,savedInstanceState);
//        if (rootView == null)
//        {
//            val inflater = LayoutInflater.from(context)
//            rootView = inflater.inflate(com.core.R.layout.dialog_loading, container)
//        }
//
//        return  rootView!!
//    }


    private var message: String? = null
    private var isCancelable = false
    private var isCancelOutside = false

    constructor(message: String?, isCancelable: Boolean, isCancelOutside: Boolean) : super() {
        this.message = message
        this.isCancelable = isCancelable
        this.isCancelOutside = isCancelOutside
    }

    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = if (context == null) super.onCreateDialog(savedInstanceState) else ProxyDialog(
            requireContext(), R.style.MyDialogStyle
        )
        val rootView = dialog.layoutInflater.inflate(R.layout.dialog_loading, null)

        rootView.findViewById<TextView>(R.id.tipTextView).text = message

//        dialog.requestWindowFeature(STYLE_NO_TITLE)
        dialog.setCancelable(isCancelable)
        dialog.setCanceledOnTouchOutside(isCancelOutside)
        setCancelable(isCancelable)

        dialog.setContentView(rootView)
        return dialog
    }

    /**
     * fix bug
     * Can not perform this action after onSaveInstanceState
     * com.eningqu.englishweekly.activity.fragment.MainFragment.showConfirmDlg(MainFragment.java:1032)
     * @param manager
     * @param tag
     */
    override fun show(manager: FragmentManager, tag: String?) {
//        super.show(manager, tag);
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }


    override fun onDismiss(dialog: DialogInterface) {
//        super.onDismiss(dialog)
        dismissAllowingStateLoss()
    }
}