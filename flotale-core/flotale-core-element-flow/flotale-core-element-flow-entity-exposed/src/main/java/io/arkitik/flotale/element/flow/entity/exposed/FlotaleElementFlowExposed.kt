package io.arkitik.flotale.element.flow.entity.exposed

import io.arkitik.flotale.action.domain.ActionDomain
import io.arkitik.flotale.action.entity.exposed.FlotaleActionTable
import io.arkitik.flotale.element.domain.ElementDomain
import io.arkitik.flotale.element.entity.exposed.FlotaleElementTable
import io.arkitik.flotale.element.flow.domain.ElementFlowDomain
import org.jetbrains.exposed.v1.jdbc.Database
import java.time.LocalDateTime

class FlotaleElementFlowExposed(
    override val uuid: String,
    override val creationDate: LocalDateTime,
    val elementUuid: String,
    val actionUuid: String,
    override val executedBy: String,
    val database: Database?,
) : ElementFlowDomain {
    override val element: ElementDomain by lazy {
        FlotaleElementTable.findIdentityByUuid(elementUuid, database)!!
    }
    override val action: ActionDomain by lazy {
        FlotaleActionTable.findIdentityByUuid(actionUuid, database)!!
    }
}
