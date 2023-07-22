package io.inst.mock

class FunctionInvocationMock {
    companion object {
        @JvmStatic @Volatile var traceStart: Long = 0
        @JvmStatic @Volatile var traceStartInvoked: Boolean = false
        @JvmStatic @Volatile var traceEnd: Long = 0
        @JvmStatic @Volatile var traceEndInvoked: Boolean = false

        @JvmStatic fun invokeStart() {
            traceStartInvoked = true
            traceStart = System.currentTimeMillis()
        }
        @JvmStatic fun invokeEnd() {
            traceEndInvoked = true
            traceEnd = System.currentTimeMillis()
        }
    }
}