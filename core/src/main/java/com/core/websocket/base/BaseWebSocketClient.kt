package com.core.websocket.base

import android.util.Log
//import okio.ByteString.Companion.toByteString
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.nio.ByteBuffer

/**
 *  @ProjectName: core2024
 *  @Package: com.core.websocket.base
 *  @Author: lu
 *  @CreateDate: 2024/2/20 17:37
 *  @Des:
 */
internal open class BaseWebSocketClient : WebSocketClient {
    private val TAG = BaseWebSocketClient::class.java.simpleName
    var isConnect=false
    constructor(serverUri: URI?) : super(serverUri)

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.i(TAG, "=======> WebSocket连接成功" +
                " HttpStatus: $handshakedata?.httpStatus" +
                " HttpStatusMessage: $ handshakedata?.httpStatusMessage "
        )
        isConnect=true
    }

    override fun onMessage(message: String?) {
        Log.i(TAG, "=======> WebSocket接收消息: $message")
    }

    override fun onMessage(bytes: ByteBuffer?) {
        super.onMessage(bytes)
        Log.i(TAG, "=======> WebSocket接收消息: " + bytes?.toString())
    }
    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d(TAG, "=======> WebSocket连接取消" +
                " code: $code" +
                " reason: $reason" +
                " remote: $remote"
        )
        isConnect=false
    }

    override fun onError(ex: Exception?) {
        Log.e(TAG, "=======> WebSocket连接异常:$ex " )
    }
}