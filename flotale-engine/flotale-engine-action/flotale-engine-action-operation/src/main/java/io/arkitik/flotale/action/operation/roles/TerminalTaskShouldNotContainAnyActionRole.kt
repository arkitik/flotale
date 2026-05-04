package io.arkitik.flotale.action.operation.roles

import io.arkitik.flotale.action.operation.errors.FlotaleActionErrors
import io.arkitik.flotale.action.sdk.dto.CreateActionDto
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.notAcceptable

/**
 * Created By Ibrahim Al-Tamimi 
 * Created At 2:11 PM, 26/07/2025
 */
internal object TerminalTaskShouldNotContainAnyActionRole : OperationRole<CreateActionDto, Unit> {
    override fun CreateActionDto.operateRole() {
        if (sourceTask.terminalTask) {
            throw FlotaleActionErrors.CANT_ADD_ACTION_TO_TERMINAL_TASK.notAcceptable()
        }
    }
}