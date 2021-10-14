package com.gdxz123.reduxandroid.data.redux.gdstore

import com.gdxz123.reduxandroid.data.redux.gdaction.GDAction
import com.gdxz123.reduxandroid.data.redux.gdstate.APPState
import com.gdxz123.reduxandroid.data.redux.source.store.Store
import com.gdxz123.reduxandroid.data.redux.source.util.stripCoroutineName
import com.gdxz123.reduxandroid.data.register.APPReducer

class MainStore<State>(mainReducer: APPReducer, initialState: State) : Store() {
    private var reducer: APPReducer = mainReducer
    var state: State = initialState
        private set(value) {
            val oldState: State = field
            field = value
            subscriptions.forEach {
                if (it.subscriber == null) {
                    subscriptions.remove(it)
                } else {
                    it.newValues(oldState, value)
                }
            }
        }

    private var isDispatching = false

    // 线程检测
    private val createThreadName = stripCoroutineName(Thread.currentThread().name)
    private fun isSameThread() = stripCoroutineName(Thread.currentThread().name) == createThreadName

    private var subscriptions: MutableSet<StoreSubscriptionBox<State>> = mutableSetOf()

    private var subscriptionsAutomaticallySkipRepeats: Boolean = true

    /**
     * State监听方法
     * @param subscriber S 监听回调类，需要实现协议StoreSubscribCallback
     * @param transform ((StoreSubscription<State>)->StoreSubscription<SelectedState>) 需要监听的state
     */
    fun <SelectedState, S : StoreSubscribCallback<SelectedState>> subscribe(subscriber: S, transform: ((StoreSubscription<State>) -> StoreSubscription<SelectedState>)?) {
        val originalSubscription = StoreSubscription<State>(sink = {

        })
        var transformedSubscription = transform?.let {
            it(originalSubscription)
        }

        if (subscriptionsAutomaticallySkipRepeats) {
            transformedSubscription = transformedSubscription?.skipRepeats()
        }
        inSubscribe(subscriber, originalSubscription, transformedSubscription)
    }

    private fun <SelectedState> subscriptionBox(
        originalSubscription: StoreSubscription<State>,
        transformedSubscription: StoreSubscription<SelectedState>?,
        subscriber: StoreSubscribCallback<SelectedState>
    ): StoreSubscriptionBox<State> {
        val subBox = StoreSubscriptionBox<State>()
        subBox.update(originalSubscription, transformedSubscription, subscriber)
        return subBox
    }

    private fun <SelectedState, S : StoreSubscribCallback<SelectedState>> inSubscribe(
        subscriber: S,
        originalSubscription: StoreSubscription<State>,
        transformedSubscription: StoreSubscription<SelectedState>?
    ) {
        subscriptions.removeAll {
            it.subscriber == subscriber
        }
        val subscriptionBox = subscriptionBox(originalSubscription, transformedSubscription, subscriber)
        subscriptions.add(subscriptionBox)

        originalSubscription.newValues(null, state)
    }

    fun unsubscribe(subscriber: AnyStoreSubscriber) {
        subscriptions.removeAll {
            it.subscriber == subscriber
        }
    }

    /**
     * 动作派发方法
     * @param action DAction<R> 执行action
     */
    fun <R> dispatch(action: GDAction<R>) {
        checkSameThread()
        dispatchFunction(action)
    }

    // Private Dispatch Method
    private fun <R> dispatchFunction(action: GDAction<R>) {
        defaultDispatch(action)
    }

    private fun <R> defaultDispatch(action: GDAction<R>) {
        if (state is APPState) {
            val newAppState: APPState
            try {
                isDispatching = true
                newAppState = reducer.reduceAlldata(action, state as APPState)
                if (!equalsState(state, newAppState)) {
                    state = newAppState as State
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isDispatching = false
        }
    }

    private fun checkSameThread() {
        assert(isSameThread()) {
            "Redux method is not in the same thread"
        }
    }

    private fun equalsState(var0: Any?, var1: Any): Boolean {
        return var0 === var1 || var0 != null && var0 == var1
    }
}