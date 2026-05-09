package io.arkitik.flotale.engine.core

import io.arkitik.flotale.engine.core.dto.ElementDetails
import io.arkitik.flotale.protocol.user.FlotaleUserTokenData

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
        addedBy: FlotaleUserTokenData,
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
        requestedBy: FlotaleUserTokenData,
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
        executedBy: FlotaleUserTokenData,
        formData: Map<String, Any> = emptyMap(),
    )

    /**
     * Checks whether a workflow has already been initiated for a given element.
     *
     * @param elementKey The unique key identifying the element.
     * @param elementType The type of the element.
     * @return `true` if the element workflow is already initiated, `false` otherwise.
     */
    fun elementExist(
        elementKey: String,
        elementType: String,
    ): Boolean

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
        requestedBy: FlotaleUserTokenData,
    ): ElementDetails
}
