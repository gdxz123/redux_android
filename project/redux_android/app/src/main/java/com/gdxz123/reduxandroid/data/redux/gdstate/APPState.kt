package com.gdxz123.reduxandroid.data.redux.gdstate

import com.gdxz123.reduxandroid.data.redux.source.state.State
import com.gdxz123.reduxandroid.data.register.APPDataModel

class APPState(initData: APPDataModel) : State() {
    var data: APPDataModel = initData
}