package io.arkitik.flotale.element.entity.exposed

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.task.domain.TaskDomain
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskTable
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime

class FlotaleElementExposed(
    override val uuid: String,
    override val creationDate: LocalDateTime,
    override val elementKey: String,
    var taskUuid: String,
    override val addedBy: String,
    val database: Database?,
) : ElementDomain {
    private var _task: TaskDomain? = null
    override var task: TaskDomain
        get() = _task ?: FlotaleTaskTable.findIdentityByUuid(taskUuid, database)!!.also { _task = it }
        set(value) {
            _task = value
            taskUuid = value.uuid
        }
}
