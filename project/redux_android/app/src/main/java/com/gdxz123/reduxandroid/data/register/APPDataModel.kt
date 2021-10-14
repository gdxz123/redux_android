package com.gdxz123.reduxandroid.data.register

import com.gdxz123.reduxandroid.data.redux.gdstate.GDState

// 注：注册变量要用val，避免直接修改
class APPDataModel(
    val testState: GDState<Int> = GDState()
)
