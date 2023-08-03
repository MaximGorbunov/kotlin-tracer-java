package io.inst

import io.inst.javassist.JvstTestRoot
import io.inst.klass.*
import io.inst.mock.DebugProbesInvocationMock
import io.inst.mock.FunctionInvocationMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.debug.DebugProbes
import org.junit.Test
import kotlin.reflect.KClass


@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineInstrumentatorTest: JvstTestRoot("coroutine-instrumentation-test") {

    @Test
    fun testDebugProbesInstrumentation() {
        val cc = sloader["kotlinx.coroutines.debug.internal.DebugProbesImpl"]
        CoroutineInstrumentator.transformKotlinCoroutines(
            cc,
            "${CoroutineInstrumentator.coroutineCreatedSrc.replace(Regex("if \\(.*"), "")}io.inst.mock.DebugProbesInvocationMock.invokeCoroutineCreated();",
            "${CoroutineInstrumentator.coroutineSuspendedSrc.replace(Regex("if \\(.*"), "")}io.inst.mock.DebugProbesInvocationMock.invokeCoroutineResumed();",
            "${CoroutineInstrumentator.coroutineResumedSrc.replace(Regex("if \\(.*"), "")}io.inst.mock.DebugProbesInvocationMock.invokeCoroutineSuspended();",
            "${CoroutineInstrumentator.coroutineCompletedSrc.replace(Regex("if \\(.*"), "")}io.inst.mock.DebugProbesInvocationMock.invokeCoroutineCompleted();"
        )
        cc.writeFile(".")
        cloader.loadClass(cc.name)
        val debugProbesClass = cloader.loadClass(DebugProbes::class.java.name)
        val mockClass = cloader.loadClass(DebugProbesTestClass::class.java.name)
        val invocationMock = cloader.loadClass(DebugProbesInvocationMock::class.java.name)
        val mock = mockClass.getConstructor().newInstance()
        val installProbesMethod = debugProbesClass.getDeclaredMethod("install")
        val inst = debugProbesClass.getField("INSTANCE").get(null)
        installProbesMethod.invoke(inst)
        mockClass.getDeclaredMethod("invoke").invoke(mock)
        assertTrue(invocationMock.getDeclaredMethod("getCoroutineCreated").invoke(null) as Boolean)
        assertTrue(invocationMock.getDeclaredMethod("getCoroutineResumed").invoke(null) as Boolean)
        assertTrue(invocationMock.getDeclaredMethod("getCoroutineSuspended").invoke(null) as Boolean)
        assertTrue(invocationMock.getDeclaredMethod("getCoroutineCompleted").invoke(null) as Boolean)
    }

    @Test fun testNonSuspendSimple() { testMethodTracing(NonSuspendSimple::class, 10) }
    @Test fun testSuspendSimple() { testMethodTracing(SuspendSimple::class, 10, true) }
    @Test fun testNonSuspendTryCase() { testMethodTracing(NonSuspendTryCase::class, 10) }
    @Test fun testSuspendTryCase() { testMethodTracing(SuspendTryCase::class, 10, true) }
    @Test fun testNonSuspendCatchCase() { testMethodTracing(NonSuspendCatchCase::class, 10) }
    @Test fun testSuspendCatchCase() { testMethodTracing(SuspendCatchCase::class, 10, true) }
    @Test fun testNonSuspendFinallyCase() { testMethodTracing(NonSuspendFinallyCase::class, 10) }
    @Test fun testSuspendFinallyCase() { testMethodTracing(SuspendFinallyCase::class, 10, true) }
    @Test fun testNonSuspendRunCatchingNormalCase() { testMethodTracing(NonSuspendRunCatchingNormalCase::class, 10) }
    @Test fun testSuspendRunCatchingNormalCase() { testMethodTracing(SuspendRunCatchingNormalCase::class, 10, true) }
    @Test fun testNonSuspendRunCatchingExceptionCase() { testMethodTracing(NonSuspendRunCatchingExceptionCase::class, 10) }
    @Test fun testSuspendRunCatchingExceptionCase() { testMethodTracing(SuspendRunCatchingExceptionCase::class, 10, true) }

    private fun testMethodTracing(klass: KClass<*>, expectedTimeElapsed: Int, suspend: Boolean = false) {
        val cc = sloader[klass.qualifiedName]
        val methodName = if (suspend) "wrapper" else "invoke"
        CoroutineInstrumentator.transformMethodForTracing(
            cc,
            methodName,
            "io.inst.mock.FunctionInvocationMock.invokeStart();",
            "io.inst.mock.FunctionInvocationMock.invokeStart();",
            "io.inst.mock.FunctionInvocationMock.invokeEnd();"
        )
        cc.writeFile(".")
        cloader.loadClass(cc.name)
        val mockClass = cloader.loadClass(klass.qualifiedName)
        val invocationMock = cloader.loadClass(FunctionInvocationMock::class.java.name)
        val mock = mockClass.getConstructor().newInstance()
        mockClass.getDeclaredMethod(methodName).invoke(mock)
        assertTrue(invocationMock.getDeclaredMethod("getTraceStartInvoked").invoke(null) as Boolean)
        val start = invocationMock.getDeclaredMethod("getTraceStart").invoke(null) as Long
        assertTrue(invocationMock.getDeclaredMethod("getTraceEndInvoked").invoke(null) as Boolean)
        val end = invocationMock.getDeclaredMethod("getTraceEnd").invoke(null) as Long
        assert(end - start >= expectedTimeElapsed)
    }
}