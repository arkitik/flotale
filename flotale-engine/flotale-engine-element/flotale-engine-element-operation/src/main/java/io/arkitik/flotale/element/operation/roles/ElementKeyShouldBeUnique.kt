package io.arkitik.flotale.element.operation.roles

import io.arkitik.flotale.element.operation.errors.FlotaleElementErrors
import io.arkitik.flotale.element.sdk.dto.ElementReferenceData
import io.arkitik.flotale.element.store.query.ElementStoreQuery
import io.arkitik.radix.develop.operation.OperationRole
import io.arkitik.radix.develop.shared.ext.unprocessableEntity

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 4:23 PM, 20 , **Tue, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
internal class ElementKeyShouldBeUnique(
    private val elementStoreQuery: ElementStoreQuery,
) : OperationRole<ElementReferenceData, Unit> {
    override fun ElementReferenceData.operateRole() {
        if (elementStoreQuery.existByElementKeyAndType(elementKey, elementType)) {
            throw FlotaleElementErrors.ELEMENT_ALREADY_EXIST.unprocessableEntity()
        }
    }

}
