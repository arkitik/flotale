package io.arkitik.flotale.plugin.jwt.validator

import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import io.arkitik.flotale.engine.function.dtos.ExecuteActionData
import io.arkitik.flotale.plugin.jwt.config.FlotaleJwtProperties
import io.arkitik.flotale.protocol.user.FlotaleUserTokenData

/**
 * @author Ibrahim Al-Tamimi 
 * @since 12:33, Saturday, 09/05/2026
 **/
internal class JwtSystemActionExecutionValidatorUnit(
    private val flotaleJwtProperties: FlotaleJwtProperties,
) : ActionExecutionValidator.ValidatorUnit {
    override fun isSupported(actionData: ExecuteActionData.Companion.Standard): Boolean {
        return actionData.actor is FlotaleUserTokenData.Companion.System
    }

    override fun canExecute(actionData: ExecuteActionData.Companion.Standard): Boolean {
        return flotaleJwtProperties.systemUserRoles.contains(actionData.actionKey)
    }
}