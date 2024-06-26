package com.core.websocket.base

import com.core.util.EasyLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.java_websocket.enums.ReadyState
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.nio.ByteBuffer

/**
 *  @ProjectName: core2024
 *  @Package: com.core.websocket.base
 *  @Author: lu
 *  @CreateDate: 2024/2/21 9:28
 *  @Des:
 */
 class CoreWebSocketClient() {
    private val TAG: String = CoreWebSocketClient::class.java.simpleName

    /**
     * 是否是主动关闭，未主动关闭会重连
     */
    private var closeFlag = false

    /**
     * 重连延迟时间
     */
    var delayTime = 3000L

    var webSocketCallBack: IWebSocketCallBack? = null

    private var client: BaseWebSocketClient? = null


    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            CoreWebSocketClient()
        }
    }

    /**
     * 连接service
     */
    fun connectService(urlString: String) {
        closeFlag = false

        if (client == null) {
            client = object : BaseWebSocketClient(URI.create(urlString)) {
                override fun onMessage(message: String?) {
                    super.onMessage(message)
                    webSocketCallBack?.onMessage(message)
                }

                override fun onMessage(bytes: ByteBuffer?) {
                    super.onMessage(bytes)
                    webSocketCallBack?.onMessage(bytes)
                }

                override fun onOpen(serverHandshake: ServerHandshake?) {
                    super.onOpen(serverHandshake)

                }

                override fun onClose(code: Int, reason: String?, remote: Boolean) {
                    super.onClose(code, reason, remote)
                    if (!closeFlag) {
                        reconnectionWebSocket()
                    }
                }

                override fun onError(ex: Exception?) {
                    super.onError(ex)

                }
            }


        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
//            client?.addHeader("User-Agent", "Android")
                if (client!!.readyState.equals(ReadyState.NOT_YET_CONNECTED)) {
                    client?.connectBlocking();
                } else if (client!!.readyState.equals(ReadyState.CLOSING) || client!!.readyState.equals(
                        ReadyState.CLOSED
                    )
                ) {
                    client?.reconnectBlocking();
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

    }

    /**
     * 是否连接
     * @return
     */
    fun isOpenWebSocket(): Boolean {
        return if (client == null) false else client!!.isOpen
    }

    /**
     * 是否关闭
     *
     * @return
     */
    fun isCloseWebSocket(): Boolean {
        return if (client == null) true else client!!.isClosed
    }

    /**
     * 重连WebSocket
     *
     */
    private fun reconnectionWebSocket() {
        GlobalScope.launch(Dispatchers.IO) {

            if (!client!!.isConnect) {
                delay(delayTime)
                synchronized(CoreWebSocketClient::class.java) {
                    try {
                        client?.reconnectBlocking()

                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                        EasyLog.DEFAULT.e(TAG, e.message!!)
                    }
                }
            }
        }
    }


    /**
     * 断开连接
     *
     */
    fun closeConnection() {
        EasyLog.DEFAULT.d(TAG, "close connect")
        try {
            client?.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        closeFlag = true
        webSocketCallBack=null
    }

    /**
     * 发送数据
     *
     * @param dataString
     */
    fun sendData(dataString: String) {
        if (isOpenWebSocket()) {
//            EasyLog.DEFAULT.e(dataString?.length)
            client?.send(dataString)
        }
    }

    /**
     * 发送数据
     *
     * @param byteArray
     */
    fun sendData(byteArray: ByteArray) {
        if (isOpenWebSocket()) {
//            EasyLog.DEFAULT.e(byteArray?.size)
            client?.send(byteArray)
        }
    }
}