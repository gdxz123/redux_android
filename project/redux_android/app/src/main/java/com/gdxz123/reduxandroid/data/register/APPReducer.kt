package com.gdxz123.reduxandroid.data.register

import com.gdxz123.reduxandroid.data.redux.gdaction.GDAction
import com.gdxz123.reduxandroid.data.redux.gdreducer.CReducer
import com.gdxz123.reduxandroid.data.redux.gdstate.APPState
import com.gdxz123.reduxandroid.data.redux.source.reducers.Reducer

class APPReducer : Reducer<ActionEnum, GDAction<Int>, APPState>() {
    fun <R> reduceAlldata(action: GDAction<R>, state: APPState): APPState {
        if (action.type == ActionEnum.AppCleanAction) {
            return APPState(APPDataModel())
        }
        val appState = APPState(
            initData = APPDataModel(
                testState = CReducer<Int, R>().reduce(ActionEnum.TestAction, action, state.data.testState)
            )
        )
        return appState
    }

    override fun reduce(type: ActionEnum, action: GDAction<Int>, state: APPState?): APPState {
        return APPState(initData = APPDataModel())
    }
}