package com.gdxz123.reduxandroid.data.redux.gdreducer

import com.gdxz123.reduxandroid.data.register.ActionEnum

interface GDReducerType<A, S> {
    fun reduce(type: ActionEnum, action: A, state: S?): S
}

abstract class GDReducer<A, S> : GDReducerType<A, S> {

}