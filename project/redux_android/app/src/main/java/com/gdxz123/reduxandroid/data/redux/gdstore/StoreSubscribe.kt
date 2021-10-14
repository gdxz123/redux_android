package com.gdxz123.reduxandroid.data.redux.gdstore

interface AnyStoreSubscriber {
    fun inNewState(state: Any)
}

interface StoreSubscribCallback<SelectedState>: AnyStoreSubscriber {
    override fun inNewState(state: Any) {
        val typedState: SelectedState? = state as? SelectedState
        if (typedState != null) {
            newState(typedState)
        }
    }

    fun newState(state: SelectedState) {

    }
}