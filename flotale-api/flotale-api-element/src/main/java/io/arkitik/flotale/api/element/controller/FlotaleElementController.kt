package io.arkitik.flotale.api.element.controller

import io.arkitik.flotale.api.element.contract.FlotaleElementContract
import io.arkitik.flotale.api.element.models.ElementAuditData
import io.arkitik.flotale.engine.core.FlotaleWorkflowEngine
import io.arkitik.flotale.engine.core.dto.ElementDetails
import io.arkitik.flotale.protocol.user.FlotaleUserTokenData
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * @author Ibrahim Al-Tamimi 
 * @since 08:27, Friday, 08/05/2026
 **/
@RestController
class FlotaleElementController(
    private val flotaleWorkflowEngine: FlotaleWorkflowEngine,
) : FlotaleElementContract {
    override fun elementDetails(
        userData: FlotaleUserTokenData,
        elementKey: String,
        elementType: String,
    ): ResponseEntity<ElementDetails> {
        return ResponseEntity.ok(
            flotaleWorkflowEngine.elementDetails(
                elementKey = elementKey,
                elementType = elementType,
                requestedBy = userData
            )
        )
    }

    override fun executeAction(
        userData: FlotaleUserTokenData,
        elementType: String,
        elementKey: String,
        actionKey: String,
        request: Map<String, Any>?,
    ): ResponseEntity<ElementDetails> {
        flotaleWorkflowEngine.executeAction(
            actionKey = actionKey,
            elementKey = elementKey,
            elementType = elementType,
            executedBy = userData,
            formData = request ?: mapOf(),
        )
        return ResponseEntity.ok(
            flotaleWorkflowEngine.elementDetails(
                elementKey = elementKey,
                elementType = elementType,
                requestedBy = userData
            )
        )
    }

    override fun elementAudit(
        userData: FlotaleUserTokenData,
        elementKey: String,
        elementType: String,
        ascending: Boolean,
    ): ResponseEntity<ElementAuditData> =
        ResponseEntity.ok(
            flotaleWorkflowEngine.elementAudit(
                elementKey = elementKey,
                elementType = elementType,
                requestedBy = userData,
                ascending = ascending
            ).let(::ElementAuditData)
        )
}