package com.gdxz123.reduxandroid.data.redux

import com.gdxz123.reduxandroid.data.register.APPDataModel
import com.gdxz123.reduxandroid.data.register.APPReducer
import com.gdxz123.reduxandroid.data.register.ActionEnum
import com.gdxz123.reduxandroid.data.redux.gdstate.APPState
import com.gdxz123.reduxandroid.data.redux.gdaction.GDAction
import com.gdxz123.reduxandroid.data.redux.gdaction.PeriodType
import com.gdxz123.reduxandroid.data.redux.gdstore.AnyStoreSubscriber
import com.gdxz123.reduxandroid.data.redux.gdstore.MainStore
import com.gdxz123.reduxandroid.data.redux.gdstore.StoreSubscribCallback
import com.gdxz123.reduxandroid.data.redux.gdstore.StoreSubscription

class StoreManager private constructor() {
    private val store: MainStore<APPState> = MainStore(mainReducer = APPReducer(),initialState = APPState(APPDataModel()))

    /**
     * State监听方法
     * @param subscriber S: StoreSubscribCallback<SelectedState>> 监听回调类
     * @param transform ((StoreSubscription<APPState>)->StoreSubscription<SelectedState>) 回调state刷选规则
     * @return  Unit
     */
    fun <SelectedState, S: StoreSubscribCallback<SelectedState>>subscribe(subscriber: S, transform: ((StoreSubscription<APPState>)->StoreSubscription<SelectedState>)) {
        store.subscribe(subscriber, transform)
    }

    /**
     * 取消订阅
     */
    fun unsubscribe(subscriber: AnyStoreSubscriber) {
        store.unsubscribe(subscriber)
    }

    /**
     * 动作派发方法
     * @param action DAction<R> 执行action
     */
    fun <R> dispatch(action: GDAction<R>) {
        store.dispatch(action)
    }

    /**
     * 获取APPState
     */
    fun getAppState(): APPState {
        return store.state
    }

    /**
     * 派发清除AppState action
     */
    fun cleanAppState() {
        val action = GDAction(type = ActionEnum.AppCleanAction, period = PeriodType.Normal, content = null)
        dispatch(action)
    }

    companion object {
        private var instan: StoreManager? = null
            get() {
                if (field == null) {
                    field = StoreManager()
                }
                return field
            }
        @Synchronized
        fun share(): StoreManager{
            return instan!!
        }
    }
}