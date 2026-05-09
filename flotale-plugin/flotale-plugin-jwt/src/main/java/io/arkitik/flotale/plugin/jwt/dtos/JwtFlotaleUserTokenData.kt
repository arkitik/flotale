package io.arkitik.flotale.plugin.jwt.dtos

import io.arkitik.flotale.protocol.user.FlotaleUserTokenData

/**
 * @author Ibrahim Al-Tamimi 
 * @since 09:19, Friday, 08/05/2026
 **/
data class JwtFlotaleUserTokenData(
    override val userId: String,
    override val username: String,
    override val email: String?,
    override val roles: Set<String>,
    override val attributes: Map<String, String?>,
) : FlotaleUserTokenData