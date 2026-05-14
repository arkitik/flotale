package io.arkitik.flotale.engine.core.dto

import java.time.LocalDateTime

data class ElementAuditEntry(
    val actionKey: String,
    val actionName: String,
    val actionColor: String,
    val actionOutlined: Boolean,
    val fromTask: TaskAuditEntry,
    val toTask: TaskAuditEntry,
    val executedBy: String,
    val executedAt: LocalDateTime,
    val executionData: Map<String, Any>? = null,
)

data class TaskAuditEntry(
    val taskKey: String,
    val taskName: String,
    val terminal: Boolean,
)