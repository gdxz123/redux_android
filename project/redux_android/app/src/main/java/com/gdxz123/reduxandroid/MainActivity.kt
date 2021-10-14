package com.gdxz123.reduxandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gdxz123.reduxandroid.data.redux.StoreManager
import com.gdxz123.reduxandroid.data.redux.gdaction.GDAction
import com.gdxz123.reduxandroid.data.redux.gdaction.PeriodType
import com.gdxz123.reduxandroid.data.redux.gdstate.GDState
import com.gdxz123.reduxandroid.data.redux.gdstore.BlockSubscriber
import com.gdxz123.reduxandroid.data.register.ActionEnum

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create subscribe
        StoreManager.share().subscribe(testSubscriber, {
            it.select { state ->
                state.data.testState
            }
        })

        // actionA
        val actionA = GDAction(type = ActionEnum.TestAction, period = PeriodType.Successed, content = 100)
        // dispatch action
        StoreManager.share().dispatch(actionA)

        // actionB
        val actionB = GDAction(type = ActionEnum.TestAction, period = PeriodType.Successed, content = 50)
        // dispatch action
        StoreManager.share().dispatch(actionB)
    }

    // subscriber
    private var testSubscriber: BlockSubscriber<GDState<Int>> = BlockSubscriber { state, isInitail ->
        state.model?.let {
            Log.d("TestState", "TestState is $it")
        }
    }
}