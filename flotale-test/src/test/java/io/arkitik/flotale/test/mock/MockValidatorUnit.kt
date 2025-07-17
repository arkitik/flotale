package io.arkitik.flotale.test.mock

import io.arkitik.flotale.engine.function.action.ActionExecutionValidator
import org.springframework.stereotype.Service

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 1:30 PM, 24 , **Sat, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
@Service
internal class MockValidatorUnit : ActionExecutionValidator.ExecutorValidatorUnit {
    private val verifiers = mutableListOf<ActionExecutionValidator.ExecutorValidatorUnit>()

    internal fun registerVerifier(validatorUnit: ActionExecutionValidator.ExecutorValidatorUnit) {
        verifiers.add(validatorUnit)
    }

    internal fun clearAll() = verifiers.clear()

    override fun isSupported(actionKey: String, elementKey: String, requestedBy: String) =
        verifiers.all {
            it.isSupported(actionKey, elementKey, requestedBy)
        }

    override fun canExecute(actionKey: String, elementKey: String, requestedBy: String) =
        verifiers.all {
            it.canExecute(actionKey, elementKey, requestedBy)
        }
}

object MockValidatorUnits {
    internal object AllAccept : ActionExecutionValidator.ExecutorValidatorUnit {
        override fun isSupported(actionKey: String, elementKey: String, requestedBy: String) = true

        override fun canExecute(actionKey: String, elementKey: String, requestedBy: String) = true
    }

    internal object SupportedAndCantExecute : ActionExecutionValidator.ExecutorValidatorUnit {
        override fun isSupported(actionKey: String, elementKey: String, requestedBy: String) = true

        override fun canExecute(actionKey: String, elementKey: String, requestedBy: String) = false
    }

    internal class SupportedAndCanExecuteForAction(private val actionKey: String) :
        ActionExecutionValidator.ExecutorValidatorUnit {
        override fun isSupported(actionKey: String, elementKey: String, requestedBy: String) = true

        override fun canExecute(actionKey: String, elementKey: String, requestedBy: String) =
            actionKey == this@SupportedAndCanExecuteForAction.actionKey
    }

    internal class ConditionalUnit(
        private val conditionSupported: (actionKey: String, elementKey: String) -> Boolean,
        private val condition: (actionKey: String, elementKey: String) -> Boolean = conditionSupported,
    ) : ActionExecutionValidator.ExecutorValidatorUnit {
        override fun isSupported(actionKey: String, elementKey: String, requestedBy: String) =
            conditionSupported(actionKey, elementKey)

        override fun canExecute(actionKey: String, elementKey: String, requestedBy: String) =
            condition(actionKey, elementKey)
    }
}
