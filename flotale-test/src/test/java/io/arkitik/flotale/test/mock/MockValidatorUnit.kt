package io.arkitik.flotale.test.mock

import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import io.arkitik.flotale.engine.function.dtos.ExecuteActionData
import org.springframework.stereotype.Service

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:30 PM, 24 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
@Service
internal class MockValidatorUnit : ActionExecutionValidator.ValidatorUnit {
    private val verifiers = mutableListOf<ActionExecutionValidator.ValidatorUnit>()

    internal fun registerVerifier(validatorUnit: ActionExecutionValidator.ValidatorUnit) {
        verifiers.add(validatorUnit)
    }

    internal fun clearAll() = verifiers.clear()

    override fun isSupported(actionData: ExecuteActionData.Companion.Standard) =
        verifiers.all {
            it.isSupported(actionData)
        }

    override fun canExecute(actionData: ExecuteActionData.Companion.Standard) =
        verifiers.all {
            it.canExecute(actionData)
        }
}

object MockValidatorUnits {
    internal object AllAccept : ActionExecutionValidator.ValidatorUnit {
        override fun isSupported(actionData: ExecuteActionData.Companion.Standard) = true

        override fun canExecute(actionData: ExecuteActionData.Companion.Standard) = true
    }

    internal object SupportedAndCantExecute : ActionExecutionValidator.ValidatorUnit {
        override fun isSupported(actionData: ExecuteActionData.Companion.Standard) = true

        override fun canExecute(actionData: ExecuteActionData.Companion.Standard) = false
    }

    internal class SupportedAndCanExecuteForAction(private val actionKey: String) :
        ActionExecutionValidator.ValidatorUnit {
        override fun isSupported(actionData: ExecuteActionData.Companion.Standard) = true

        override fun canExecute(actionData: ExecuteActionData.Companion.Standard) =
            actionKey == actionData.actionKey
    }

    internal class ConditionalUnit(
        private val conditionSupported: (actionKey: String, elementKey: String) -> Boolean,
        private val condition: (actionKey: String, elementKey: String) -> Boolean = conditionSupported,
    ) : ActionExecutionValidator.ValidatorUnit {
        override fun isSupported(actionData: ExecuteActionData.Companion.Standard) =
            conditionSupported(actionData.actionKey, actionData.elementKey)

        override fun canExecute(actionData: ExecuteActionData.Companion.Standard) =
            condition(actionData.actionKey, actionData.elementKey)
    }
}
