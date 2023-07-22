package io.inst.klass

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class DebugProbesTestClass {
    fun invoke() {
        runBlocking {
            suspendExample()
        }
    }

    private suspend fun suspendExample() {
        delay(1)
    }
}