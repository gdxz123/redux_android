package com.gdxz123.reduxandroid.data.redux.gdstore

import com.gdxz123.reduxandroid.data.redux.StoreManager
import kotlin.Unit;

// SelectState: 监听的state类型， Boolean 是否是初始监听导致的触发回调
typealias BlockSubscribeCallback<SelectState> = (SelectState, Boolean) -> Unit

/**
 *  回调监听state类
 *  @param  onHandler BlockSubscribeCallback<SelectState> 监听回调,SelectState监听的数据类型，Boolean 是否是第一次监听导致的触发
 *  @return StoreSubscribCallback 回调处理方法
 */
class BlockSubscriber<SelectState>(onHandler: BlockSubscribeCallback<SelectState>): StoreSubscribCallback<SelectState> {
    private var isInitial: Boolean = true
    private var onCallback: BlockSubscribeCallback<SelectState> = onHandler
    override fun newState(state: SelectState) {
        super.newState(state)
        onCallback(state, isInitial)
        isInitial = false
    }

    /**
     *  请求监听方法
     *  当持有类释放时，需要清空当前监听
     *  @return Unit
     */
    fun clean() {
        StoreManager.share().unsubscribe(this)
    }
}
