package io.arkitik.flotale.api.authenticated.controller

import io.arkitik.flotale.api.authenticated.contract.FlotaleAuthenticatedContract
import io.arkitik.flotale.api.authenticated.dtos.FlotaleUserData
import io.arkitik.flotale.engine.core.FlotaleWorkflowEngine
import io.arkitik.flotale.engine.core.dto.ElementDetails
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * Created By Ibrahim Al-Tamimi 
 * Created At 10:35 PM, 03/08/2025
 */
@RestController
class FlotaleAuthenticatedController(
    private val flotaleWorkflowEngine: FlotaleWorkflowEngine,
) : FlotaleAuthenticatedContract {
    override fun executeAction(
        elementKey: String,
        actionKey: String,
        userData: FlotaleUserData,
    ) {
        flotaleWorkflowEngine.executeAction(
            actionKey = actionKey,
            elementKey = elementKey,
            executedBy = userData.username,
        )
    }

    override fun elementDetails(
        elementKey: String,
        userData: FlotaleUserData,
    ): ResponseEntity<ElementDetails> {
        return ResponseEntity.ok(
            flotaleWorkflowEngine.elementDetails(
                elementKey = elementKey,
                requestedBy = userData.username,
            )
        )
    }
}