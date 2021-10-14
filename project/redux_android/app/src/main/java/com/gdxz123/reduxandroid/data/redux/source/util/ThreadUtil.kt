package com.gdxz123.reduxandroid.data.redux.source.util

/**
 * 获取可比较的线程名字方法
 * 线程名字可能存在 '@coroutine#n'
 * 做好匹配才能比较
 * @param threadName String 线程名字
 * @return 可比较的线程比较
 */
fun stripCoroutineName(threadName: String): String {
    val lastIndex = threadName.lastIndexOf('@')
    return if (lastIndex < 0) threadName
    else threadName.substring(0, lastIndex)
}