package com.core.bean

/**
 *  @ProjectName: core2024
 *  @Package: com.core.bean
 *  @Author: lu
 *  @CreateDate: 2024/2/19 11:13
 *  @Des:
 */
 class EventBusCarrier {
    var eventType //区分事件的类型
            = 0
    var  eventObj//事件的实体类
            : Any? = null

    constructor(eventType: Int, eventObj: Any?) {
        this.eventType = eventType
        this.eventObj = eventObj
    }

    constructor(eventType: Int) {
        this.eventType = eventType
    }
}