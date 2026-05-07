package io.arkitik.flotale.engine.core

import io.arkitik.flotale.engine.core.dto.ElementDetails

/**
 * Interface representing the core workflow engine for managing and executing workflow elements.
 * Provides methods to initiate elements, validate and execute actions, and retrieve element details.
 *
 *
 * @author Ibrahim Al-Tamimi 
 * @since 19:46, Tuesday, 20/12/2022
 **/
interface FlotaleWorkflowEngine {

    /**
     * Initiates a workflow element with the given details.
     *
     * @param workflowKey The unique key identifying the workflow.
     * @param elementKey The unique key identifying the element to be initiated.
     * @param addedBy The identifier of the user who is adding the element.
     * @return [ElementDetails] containing details of the initiated element.
     */
    fun initiateElement(
        workflowKey: String,
        elementKey: String,
        elementType: String,
        addedBy: String,
    ): ElementDetails

    /**
     * Validates whether an action can be executed on a specific element.
     *
     * @param actionKey The unique key identifying the action to be validated.
     * @param elementKey The unique key identifying the element on which the action is to be validated.
     * @param requestedBy The identifier of the user requesting the validation.
     */
    fun validateExecuteAction(
        actionKey: String,
        elementKey: String,
        elementType: String,
        requestedBy: String,
    )

    /**
     * Executes an action on a specific element.
     *
     * @param actionKey The unique key identifying the action to be executed.
     * @param elementKey The unique key identifying the element on which the action is to be executed.
     * @param executedBy The identifier of the user executing the action.
     */
    fun executeAction(
        actionKey: String,
        elementKey: String,
        elementType: String,
        executedBy: String,
        data: Map<String, Any> = emptyMap(),
    )

    /**
     * Retrieves the details of a specific element.
     *
     * @param elementKey The unique key identifying the element whose details are to be retrieved.
     * @param requestedBy The identifier of the user requesting the element details.
     * @return [ElementDetails] containing details of the requested element.
     */
    fun elementDetails(
        elementKey: String,
        elementType: String,
        requestedBy: String,
    ): ElementDetails
}
