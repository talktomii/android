package com.furniture.utlis
import android.os.Handler

class DelayHandler {
    var handler: Handler? = null
    var runable: Runnable? = null

    init {
        handler = Handler()

    }

    fun startDealay(sec: Long, listner: () -> Unit) {

        runable = Runnable { listner.invoke() }
        handler?.postDelayed(runable!!, sec)
    }

    fun cancelHandler() {
        if (handler != null) {
            runable?.let { handler?.removeCallbacks(it) }
        }
    }
}