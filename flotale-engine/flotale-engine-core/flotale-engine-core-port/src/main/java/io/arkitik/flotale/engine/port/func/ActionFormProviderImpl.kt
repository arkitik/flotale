package io.arkitik.flotale.engine.port.func

import io.arkitik.flotale.engine.function.EngineBeanStore
import io.arkitik.flotale.engine.function.action.ActionFormProvider
import io.arkitik.flotale.engine.function.dtos.FormValidationResult
import io.arkitik.flotale.engine.port.errors.EngineFormErrors
import io.arkitik.flotale.protocol.form.ActionForm

internal class ActionFormProviderImpl(
    private val engineBeanStores: List<EngineBeanStore>,
) : ActionFormProvider {
    override fun provideForm(actionKey: String, elementKey: String, elementType: String): ActionForm? =
        engineBeanStores.flatMap { it.actionFormProviderUnits(actionKey) }
            .firstOrNull { it.isSupported(actionKey, elementKey, elementType) }
            ?.provideForm(actionKey, elementKey, elementType)

    override fun validateForm(
        actionKey: String,
        elementKey: String,
        elementType: String,
        formData: Map<String, Any?>,
    ): FormValidationResult {
        return engineBeanStores.flatMap { it.actionFormProviderUnits(actionKey) }
            .firstOrNull { it.isSupported(actionKey, elementKey, elementType) }
            ?.validateForm(actionKey, elementKey, elementType, formData) ?: FormValidationResult.invalid(
            listOf(EngineFormErrors.INVALID_FORM_DATA)
        )
    }
}
