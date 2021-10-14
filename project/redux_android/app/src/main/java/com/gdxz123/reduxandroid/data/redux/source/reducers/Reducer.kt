package com.gdxz123.reduxandroid.data.redux.source.reducers

interface ReducerType<T, A, S> {
    fun reduce(type: T, action: A, state: S?): S
}

abstract class Reducer<T, A, S>: ReducerType<T, A, S> {

}