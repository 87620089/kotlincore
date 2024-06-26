package com.core.websocket.base

import java.nio.ByteBuffer

/**
 *  @ProjectName: core2024
 *  @Package: com.core.websocket.base
 *  @Author: lu
 *  @CreateDate: 2024/2/21 9:52
 *  @Des:
 */
interface IWebSocketCallBack {

    fun onMessage(message: String?){

    }

    fun onMessage(bytes: ByteBuffer?){

    }
}