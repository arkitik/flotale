package io.arkitik.flotale.engine.port.func

import io.arkitik.flotale.engine.function.EngineBeanStore
import io.arkitik.flotale.engine.function.action.ActionFormProvider
import io.arkitik.flotale.protocol.form.ActionForm

internal class ActionFormProviderImpl(
    private val engineBeanStores: List<EngineBeanStore>,
) : ActionFormProvider {
    override fun provideForm(actionKey: String, elementKey: String, elementType: String): ActionForm? =
        engineBeanStores.flatMap { it.actionFormProviderUnits(actionKey) }
            .firstOrNull { it.isSupported(actionKey, elementKey, elementType) }
            ?.provideForm(actionKey, elementKey, elementType)
}
