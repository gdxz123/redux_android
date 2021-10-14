package com.gdxz123.reduxandroid.data.redux.gdaction

import com.gdxz123.reduxandroid.data.redux.source.action.Action
import com.gdxz123.reduxandroid.data.register.ActionEnum

open class GDAction<T> constructor(type: ActionEnum, period: PeriodType, content: T? = null) : Action() {
    var type: ActionEnum = ActionEnum.Undefined
    var period: PeriodType = PeriodType.Normal
    var content: T? = null

    init {
        this.type = type
        this.period = period
        this.content = content
    }

    private val clsName: String = javaClass.simpleName
    override fun toString(): String {
        return clsName
    }
}