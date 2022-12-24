package io.arkitik.flotale.task.initial.adapter.repository

import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.task.entity.FlotaleTask
import io.arkitik.flotale.task.initial.entity.FlotaleTaskInitial
import io.arkitik.radix.adapter.shared.repository.RadixRepository

interface TaskInitialRepository : RadixRepository<String, FlotaleTaskInitial> {
    fun existsByStage(stage: FlotaleStage): Boolean
    fun findByStage(stage: FlotaleStage): FlotaleTaskInitial?

    fun findByTask(task: FlotaleTask): FlotaleTaskInitial?
}
