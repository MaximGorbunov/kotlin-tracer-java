package io.inst.klass

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class NonSuspendSimple {
    fun invoke() {
        Thread.sleep(10)
    }
}

class NonSuspendTryCase {
    fun invoke() {
        try {
            Thread.sleep(10)
        } catch (e: Exception) {
            throw e
        }
    }
}

class NonSuspendCatchCase {
    fun invoke() {
        try {
            throw RuntimeException()
        } catch (e: Exception) {
            Thread.sleep(10)
        }
    }
}

class NonSuspendFinallyCase {
    fun invoke() {
        try {
            throw RuntimeException()
        } catch (e: Exception) {
        } finally {
            Thread.sleep(10)
        }
    }
}

class NonSuspendRunCatchingNormalCase {
    fun invoke() = runCatching {
        Thread.sleep(10)
    }.getOrNull()
}

class NonSuspendRunCatchingExceptionCase {
    fun invoke() = runCatching {
        throw RuntimeException()
    }.recover {
        Thread.sleep(10)
    }.getOrNull()
}
/////////////////////////////////////////////////////////////////////////

class SuspendSimple {
    fun wrapper() = runBlocking { invoke() }

    suspend fun invoke() {
        delay(10)
    }
}

class SuspendTryCase {
    fun wrapper() = runBlocking { invoke() }

    suspend fun invoke() {
        try {
            delay(10)
        } catch (e: Exception) {
            throw e
        }
    }
}

class SuspendCatchCase {
    fun wrapper() = runBlocking { invoke() }

    suspend fun invoke() {
        try {
            throw RuntimeException()
        } catch (e: Exception) {
            delay(10)
        }
    }
}

class SuspendFinallyCase {
    fun wrapper() = runBlocking { invoke() }

    suspend fun invoke() {
        try {
            throw RuntimeException()
        } catch (e: Exception) {
        } finally {
            delay(10)
        }
    }
}

class SuspendRunCatchingNormalCase {
    fun wrapper() = runBlocking { invoke() }

    suspend fun invoke() = runCatching {
        delay(10)
    }.getOrNull()
}

class SuspendRunCatchingExceptionCase {
    fun wrapper() = runBlocking { invoke() }

    suspend fun invoke() = runCatching {
        throw RuntimeException()
    }.recover {
        delay(10)
    }.getOrNull()
}