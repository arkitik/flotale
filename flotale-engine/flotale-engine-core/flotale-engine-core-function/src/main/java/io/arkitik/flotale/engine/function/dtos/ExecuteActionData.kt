package io.arkitik.flotale.engine.function.dtos

/**
 * @author Ibrahim Al-Tamimi 
 * @since 21:38, Tuesday, 05/05/2026
 **/
sealed class ExecuteActionData(
    val actionKey: String,
    val elementKey: String,
    val elementType: String,
    val actor: String,
) {
    companion object {
        class Standard(
            actionKey: String,
            elementKey: String,
            elementType: String,
            actor: String,
        ) : ExecuteActionData(actionKey, elementKey, elementType, actor)

        class Form(
            actionKey: String,
            elementKey: String,
            elementType: String,
            actor: String,
            val data: Map<String, Any>,
        ) : ExecuteActionData(actionKey, elementKey, elementType, actor)

        fun standard(
            actionKey: String,
            elementKey: String,
            elementType: String,
            actor: String,
        ) = Standard(actionKey, elementKey, elementType, actor)

        fun form(
            actionKey: String,
            elementKey: String,
            elementType: String,
            actor: String,
            data: Map<String, Any>,
        ) = Form(
            actionKey = actionKey,
            elementKey = elementKey,
            elementType = elementType,
            actor = actor,
            data = data,
        )
    }
}