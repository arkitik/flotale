package io.arkitik.flotale.plugin.jwt.validator

import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import io.arkitik.flotale.engine.function.dtos.ExecuteActionData

/**
 * @author Ibrahim Al-Tamimi 
 * @since 12:33, Saturday, 09/05/2026
 **/
internal class JwtActionExecutionValidatorUnit : ActionExecutionValidator.ValidatorUnit {
    override fun isSupported(actionData: ExecuteActionData.Companion.Standard): Boolean {
        return true
    }

    override fun canExecute(actionData: ExecuteActionData.Companion.Standard): Boolean {
        return actionData.actor.roles.contains(actionData.actionKey)
    }
}