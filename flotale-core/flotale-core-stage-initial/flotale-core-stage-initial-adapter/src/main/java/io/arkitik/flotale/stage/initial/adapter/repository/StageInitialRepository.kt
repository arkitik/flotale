package io.arkitik.flotale.stage.initial.adapter.repository

import io.arkitik.flotale.stage.entity.FlotaleStage
import io.arkitik.flotale.stage.initial.entity.FlotaleStageInitial
import io.arkitik.flotale.workflow.entity.FlotaleWorkflow
import io.arkitik.radix.adapter.shared.repository.RadixRepository

interface StageInitialRepository : RadixRepository<String, FlotaleStageInitial> {
    fun existsByWorkflow(workflow: FlotaleWorkflow): Boolean
    fun findByWorkflow(workflow: FlotaleWorkflow): FlotaleStageInitial?

    fun findByStage(stage: FlotaleStage): FlotaleStageInitial?
}
