package io.arkitik.flotale.element.operation

import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.flow.store.ElementFlowStore
import io.arkitik.flotale.element.operation.main.ElementExecuteActionOperation
import io.arkitik.flotale.element.operation.main.FindElementByReferenceOperation
import io.arkitik.flotale.element.operation.main.InitiateElementOperation
import io.arkitik.flotale.element.operation.roles.ElementKeyShouldBeUnique
import io.arkitik.flotale.element.sdk.FlotaleElementDomainSdk
import io.arkitik.flotale.element.sdk.dto.CreateElementDto
import io.arkitik.flotale.element.sdk.dto.ElementActionDto
import io.arkitik.flotale.element.store.ElementStore
import io.arkitik.radix.develop.operation.Operation
import io.arkitik.radix.develop.operation.ext.operateRole
import io.arkitik.radix.develop.operation.ext.operationBuilder

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 4:16 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
class FlotaleElementDomainSdkImpl(
    elementStore: ElementStore,
    elementFlowStore: ElementFlowStore,
) : FlotaleElementDomainSdk {
    private val elementKeyShouldBeUnique = ElementKeyShouldBeUnique(elementStore.storeQuery)

    override val createElement: Operation<CreateElementDto, Unit> =
        operationBuilder {
            install {
                elementKeyShouldBeUnique.operateRole(elementKey)
            }

            mainOperation(InitiateElementOperation(elementStore))
        }

    override val findElementByReference: Operation<String, ElementDomain> =
        operationBuilder {
            mainOperation(FindElementByReferenceOperation(elementStore.storeQuery))
        }
    override val elementExecuteAction: Operation<ElementActionDto, Unit> =
        operationBuilder {
            mainOperation(
                ElementExecuteActionOperation(
                    elementStore = elementStore,
                    elementFlowStore = elementFlowStore,
                )
            )
        }
}
