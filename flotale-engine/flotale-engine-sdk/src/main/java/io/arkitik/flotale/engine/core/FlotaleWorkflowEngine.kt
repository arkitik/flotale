package io.arkitik.flotale.engine.core

import io.arkitik.flotale.engine.core.dto.ElementDetails

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 7:46 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
interface FlotaleWorkflowEngine {
    fun initiateElement(workflowKey: String, elementKey: String, addedBy: String)
    fun validateExecuteAction(actionKey: String, elementKey: String, requestedBy: String)
    fun executeAction(actionKey: String, elementKey: String, executedBy: String)

    fun elementDetails(
        elementKey: String,
        requestedBy: String,
    ): ElementDetails
}
