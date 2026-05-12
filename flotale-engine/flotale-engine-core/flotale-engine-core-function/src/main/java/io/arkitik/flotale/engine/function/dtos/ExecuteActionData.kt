package io.arkitik.flotale.engine.function.dtos

import io.arkitik.flotale.protocol.user.FlotaleUserTokenData

/**
 * @author Ibrahim Al-Tamimi 
 * @since 21:38, Tuesday, 05/05/2026
 **/
sealed class ExecuteActionData(
    val actionKey: String,
    val elementKey: String,
    val elementType: String,
    val actor: FlotaleUserTokenData,
) {
    companion object {
        class Standard(
            actionKey: String,
            elementKey: String,
            elementType: String,
            actor: FlotaleUserTokenData,
        ) : ExecuteActionData(actionKey, elementKey, elementType, actor)

        class Form(
            actionKey: String,
            elementKey: String,
            elementType: String,
            actor: FlotaleUserTokenData,
            val formData: Map<String, Any>,
        ) : ExecuteActionData(actionKey, elementKey, elementType, actor)

        fun standard(
            actionKey: String,
            elementKey: String,
            elementType: String,
            actor: FlotaleUserTokenData,
        ) = Standard(actionKey, elementKey, elementType, actor)

        fun form(
            actionKey: String,
            elementKey: String,
            elementType: String,
            actor: FlotaleUserTokenData,
            formData: Map<String, Any>,
        ) = Form(
            actionKey = actionKey,
            elementKey = elementKey,
            elementType = elementType,
            actor = actor,
            formData = formData,
        )
    }
}