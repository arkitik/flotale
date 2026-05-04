package io.arkitik.flotale.engine.function.action

import io.arkitik.flotale.protocol.form.ActionForm

interface ActionFormProvider {
    fun provideForm(actionKey: String, elementKey: String, elementType: String): ActionForm?

    interface FormProviderUnit {
        fun isSupported(actionKey: String, elementKey: String, elementType: String): Boolean
        fun provideForm(actionKey: String, elementKey: String, elementType: String): ActionForm
    }
}
