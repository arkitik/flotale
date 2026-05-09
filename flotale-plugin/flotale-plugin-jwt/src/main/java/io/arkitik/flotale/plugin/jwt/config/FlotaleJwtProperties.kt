package io.arkitik.flotale.plugin.jwt.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.DefaultValue
import org.springframework.core.io.Resource

@ConfigurationProperties(prefix = "flotale.jwt")
data class FlotaleJwtProperties(
    val secret: String? = null,
    val publicKey: PublicKey? = null,
    val claims: ClaimsMapping = ClaimsMapping(),
    @DefaultValue("true")
    val defaultValidator: Boolean = true,
    val systemUserRoles: List<String> = listOf(),
) {
    data class ClaimsMapping(
        val userId: String = "sub",
        val username: String = "preferred_username",
        val email: String = "email",
        val roles: String = "roles",
    )

    data class PublicKey(
        val algorithm: String = "RSA",
        val content: Resource,
    )
}