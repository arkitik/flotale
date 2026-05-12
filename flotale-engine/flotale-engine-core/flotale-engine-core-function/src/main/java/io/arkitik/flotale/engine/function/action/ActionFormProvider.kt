package io.arkitik.flotale.engine.function.action

import io.arkitik.flotale.engine.function.dtos.ExecuteActionData
import io.arkitik.flotale.engine.function.dtos.FormValidationResult
import io.arkitik.flotale.protocol.form.ActionForm

interface ActionFormProvider {
    fun provideForm(userAction: ExecuteActionData): ActionForm?

    fun validateForm(userAction: ExecuteActionData.Companion.Form): FormValidationResult

    interface FormProviderUnit {
        fun isSupported(userAction: ExecuteActionData): Boolean
        fun provideForm(userAction: ExecuteActionData): ActionForm

        fun validateForm(userAction: ExecuteActionData.Companion.Form): FormValidationResult
    }
}
