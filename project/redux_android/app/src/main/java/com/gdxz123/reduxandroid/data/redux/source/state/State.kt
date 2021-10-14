package com.gdxz123.reduxandroid.data.redux.source.state

import java.util.*

interface StateType {}

open class State: StateType  {
    private var identifier: Int = Objects.hashCode(this)
}