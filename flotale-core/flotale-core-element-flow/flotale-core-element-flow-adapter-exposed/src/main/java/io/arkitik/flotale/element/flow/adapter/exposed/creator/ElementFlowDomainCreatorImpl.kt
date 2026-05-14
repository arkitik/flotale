package io.arkitik.flotale.element.flow.adapter.exposed.creator

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import io.arkitik.flotale.element.flow.entity.exposed.FlotaleElementFlowExposed
import io.arkitik.flotale.element.flow.store.creator.ElementFlowDomainCreator
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime
import kotlin.uuid.Uuid

internal class ElementFlowDomainCreatorImpl(
    private val database: Database?,
) : ElementFlowDomainCreator {

    private var uuid: String = Uuid.generateV7().toString().replace("-", "")
    private lateinit var element: ElementDomain
    private lateinit var action: ActionDomain
    private lateinit var executedBy: String
    private var executionData: ByteArray? = null

    override fun String.uuid(): ElementFlowDomainCreator {
        this@ElementFlowDomainCreatorImpl.uuid = this
        return this@ElementFlowDomainCreatorImpl
    }

    override fun ElementDomain.element(): ElementFlowDomainCreator {
        this@ElementFlowDomainCreatorImpl.element = this
        return this@ElementFlowDomainCreatorImpl
    }

    override fun ActionDomain.action(): ElementFlowDomainCreator {
        this@ElementFlowDomainCreatorImpl.action = this
        return this@ElementFlowDomainCreatorImpl
    }

    override fun String.executedBy(): ElementFlowDomainCreator {
        this@ElementFlowDomainCreatorImpl.executedBy = this
        return this@ElementFlowDomainCreatorImpl
    }

    override fun ByteArray?.executionData(): ElementFlowDomainCreator {
        this@ElementFlowDomainCreatorImpl.executionData = this
        return this@ElementFlowDomainCreatorImpl
    }

    override fun create(): ElementFlowDomain = FlotaleElementFlowExposed(
        uuid = uuid,
        creationDate = LocalDateTime.now(),
        elementUuid = element.uuid,
        actionUuid = action.uuid,
        executedBy = executedBy,
        executionData = executionData,
        database = database,
    )
}
