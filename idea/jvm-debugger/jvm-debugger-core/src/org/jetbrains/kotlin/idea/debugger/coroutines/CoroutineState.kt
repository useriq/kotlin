/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.debugger.coroutines

import com.sun.jdi.*
import org.jetbrains.kotlin.idea.debugger.evaluate.ExecutionContext
import org.jetbrains.kotlin.idea.debugger.isSubtype

/**
 * Represents state of a coroutine.
 * @see `kotlinx.coroutines.debug.CoroutineInfo`
 */
class CoroutineState(
    val name: String,
    val state: State,
    val thread: ThreadReference? = null,
    val stackTrace: List<StackTraceElement>,
    val creationStackTrace: List<StackTraceElement>,
    val frame: ObjectReference?
) {
    val stringStackTrace: String by lazy {
        buildString {
            appendln("\"$name\", state: $state")
            stackTrace.forEach {
                appendln("\t$it")
            }
        }
    }

    fun isSuspended() = state == State.SUSPENDED

    fun isCreated() = state == State.CREATED

    fun isEmptyStackTrace() = stackTrace.isEmpty()

    enum class State {
        RUNNING,
        SUSPENDED,
        CREATED
    }
}