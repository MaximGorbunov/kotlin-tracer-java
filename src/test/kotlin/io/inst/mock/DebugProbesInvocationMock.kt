package io.inst.mock

class DebugProbesInvocationMock {
    companion object {
        @JvmStatic @Volatile var coroutineCreated: Boolean = false
        @JvmStatic @Volatile var coroutineResumed: Boolean = false
        @JvmStatic @Volatile var coroutineSuspended: Boolean = false
        @JvmStatic @Volatile var coroutineCompleted: Boolean = false

        @JvmStatic fun invokeCoroutineCreated() {
            coroutineCreated = true
        }
        @JvmStatic fun invokeCoroutineResumed() {
            coroutineResumed = true
        }
        @JvmStatic fun invokeCoroutineSuspended() {
            coroutineSuspended = true
        }
        @JvmStatic fun invokeCoroutineCompleted() {
            coroutineCompleted = true
        }
    }
}