package com.core

import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import com.core.util.EasyLog


/**
 *  @ProjectName: core2024
 *  @Package: com.core
 *  @Author: lu
 *  @CreateDate: 2024/2/20 10:05
 *  @Des:
 */
open class BaseApplication : Application() {

    private  lateinit var cm: ConnectivityManager

    private val netCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            EasyLog.DEFAULT.e("onAvailable")
            onNetAvailable()
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            EasyLog.DEFAULT.i("onLosing")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            EasyLog.DEFAULT.e("onLost")
            onNetLost()
        }

        override fun onUnavailable() {
            super.onUnavailable()
            EasyLog.DEFAULT.e("onUnavailable")
            onNetUnavailable()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
//            EasyLog.DEFAULT.i("onCapabilitiesChanged")
        }

        override fun onLinkPropertiesChanged(
            network: Network,
            linkProperties: LinkProperties
        ) {
            super.onLinkPropertiesChanged(network, linkProperties)
//            EasyLog.DEFAULT.i("onLinkPropertiesChanged")
        }

        override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
            super.onBlockedStatusChanged(network, blocked)
//            EasyLog.DEFAULT.i("onBlockedStatusChanged")
        }
    }

    protected open fun onNetAvailable() {

    }

    protected open fun onNetLost() {

    }

    protected open fun onNetUnavailable() {

    }

    companion object {
        lateinit var sInstance: BaseApplication
        fun getInstance(): BaseApplication{
            return sInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        sInstance=this
        observeNetworkChanges()
    }



    private fun observeNetworkChanges() {
        cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(netCallBack)
    }

    override fun unregisterComponentCallbacks(callback: ComponentCallbacks?) {
        super.unregisterComponentCallbacks(callback)
    }

    override fun unregisterActivityLifecycleCallbacks(callback: ActivityLifecycleCallbacks?) {
        super.unregisterActivityLifecycleCallbacks(callback)
        cm.unregisterNetworkCallback(netCallBack)
    }

    override fun unregisterOnProvideAssistDataListener(callback: OnProvideAssistDataListener?) {
        super.unregisterOnProvideAssistDataListener(callback)
    }
}