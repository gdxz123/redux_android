package com.gdxz123.reduxandroid.data.redux.gdreducer

import com.gdxz123.reduxandroid.data.register.ActionEnum
import com.gdxz123.reduxandroid.data.redux.gdaction.GDAction
import com.gdxz123.reduxandroid.data.redux.gdstate.GDState

class CReducer<M, R> : GDReducer<GDAction<R>, GDState<M>>() {
    override fun reduce(type: ActionEnum, action: GDAction<R>, state: GDState<M>?): GDState<M> {
        var newState = state ?: GDState()
        if (action.type == type) {
            newState = GDState()
            newState.status = action.period
            val data = action.content as? M
            newState.model = data
        }
        return newState
    }
}