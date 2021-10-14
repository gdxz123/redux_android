# redux_android
redux with kotlin

# Introduction

redux_android is a [Redux](https://github.com/reactjs/redux)-like implementation of the unidirectional data flow architecture in kotlin.

# Usage

```kotlin

// subscriber
private var testSubscriber: BlockSubscriber<GDState<Int>> = BlockSubscriber { state, isInitail ->
    state.model?.let {
        Log.d("TestState", "TestState is $it")
    }
}


// create subscribe
StoreManager.share().subscribe(testSubscriber, {
    it.select { state ->
        state.data.testState
    }
})

// create action
val actionA = GDAction(type = ActionEnum.TestAction, period = PeriodType.Successed, content = 100)

// dispatch action
StoreManager.share().dispatch(actionA)
```
