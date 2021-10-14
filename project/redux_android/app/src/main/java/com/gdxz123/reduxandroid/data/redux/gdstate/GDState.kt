package com.gdxz123.reduxandroid.data.redux.gdstate

import com.gdxz123.reduxandroid.data.redux.gdaction.PeriodType
import com.gdxz123.reduxandroid.data.redux.source.state.State

open class GDState<M>(): State() {
    var status: PeriodType = PeriodType.Normal
    var model: M? = null
}