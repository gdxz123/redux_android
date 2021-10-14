package com.gdxz123.reduxandroid.data.redux.gdstore

import java.util.Objects.hashCode

class StoreSubscriptionBox<State> {
    private var originalSubscription: StoreSubscription<State>? = null
    var subscriber: AnyStoreSubscriber? = null
    private var objectIdentifier: Int = hashCode(this)

    fun <T>update(originalSubscription: StoreSubscription<State>, transformedSubscription: StoreSubscription<T>?, subscriber: AnyStoreSubscriber) {
        this.originalSubscription = originalSubscription
        this.subscriber = subscriber
        this.objectIdentifier = hashCode(subscriber)
        if (transformedSubscription != null) {
            transformedSubscription.observer = { _, newState ->
                this.subscriber?.inNewState(state = newState as Any)
            }
        } else {
            originalSubscription.observer = { _, newState ->
                this.subscriber?.inNewState(state = newState as Any)
            }
        }
    }

    fun newValues(oldState: State, newState: State) {
        this.originalSubscription?.newValues(oldState, newState)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as StoreSubscriptionBox<*>
        if (other.objectIdentifier == this.objectIdentifier) return true
        return false
    }
}

class StoreSubscription<State> {
    constructor(sink: ((State?, State) -> Unit) -> Unit) {
        sink { old, new ->
            newValues(old, new)
        }
    }

    var observer: ((State?, State) -> Unit)? = null

    private fun <Substate> inSelect(selector: (State) -> Substate): StoreSubscription<Substate> {
        return StoreSubscription<Substate> { sink ->
            observer = { oldState, newState ->
                var subStateOld: Substate? = null
                if (oldState != null) {
                    subStateOld = selector(oldState)
                }
                val subStateNew: Substate = selector(newState)
                sink(subStateOld, subStateNew)
            }
        }
    }

    fun <Substate> select(selector: (State) -> Substate): StoreSubscription<Substate> {
        return inSelect(selector)
    }

    fun newValues(oldState: State?, newState: State) {
        observer?.let{
            it(oldState, newState)
        }
    }

    fun skipRepeats(): StoreSubscription<State>{
        return StoreSubscription { sink ->
            this.observer = { oldState, newState ->
                if (oldState != null) {
                    if (oldState == newState) {
                        // 变量内存地址相同就不派发
                        Unit
                    } else {
                        sink(oldState, newState)
                    }
                } else {
                    sink(oldState, newState)
                }
            }
        }
    }
}