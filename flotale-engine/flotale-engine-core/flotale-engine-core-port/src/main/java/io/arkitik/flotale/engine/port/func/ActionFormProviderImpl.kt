package io.arkitik.flotale.engine.port.func

import io.arkitik.flotale.engine.function.EngineBeanStore
import io.arkitik.flotale.engine.function.action.ActionFormProvider
import io.arkitik.flotale.engine.function.dtos.ExecuteActionData
import io.arkitik.flotale.engine.function.dtos.FormValidationResult
import io.arkitik.flotale.engine.port.errors.EngineFormErrors
import io.arkitik.flotale.protocol.form.ActionForm

internal class ActionFormProviderImpl(
    private val engineBeanStores: List<EngineBeanStore>,
) : ActionFormProvider {
    override fun provideForm(userAction: ExecuteActionData): ActionForm? =
        engineBeanStores.flatMap { it.actionFormProviderUnits(userAction.actionKey) }
            .firstOrNull { it.isSupported(userAction) }
            ?.provideForm(userAction)

    override fun validateForm(
        userAction: ExecuteActionData.Companion.Form,
    ): FormValidationResult {
        return engineBeanStores.flatMap { it.actionFormProviderUnits(userAction.actionKey) }
            .firstOrNull { it.isSupported(userAction) }
            ?.validateForm(userAction) ?: FormValidationResult.invalid(
            listOf(EngineFormErrors.INVALID_FORM_DATA)
        )
    }
}
