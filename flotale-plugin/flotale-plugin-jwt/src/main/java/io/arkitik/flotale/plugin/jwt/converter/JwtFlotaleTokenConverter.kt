package io.arkitik.flotale.plugin.jwt.converter

import io.arkitik.flotale.plugin.jwt.config.FlotaleJwtProperties
import io.arkitik.flotale.plugin.jwt.dtos.JwtFlotaleUserTokenData
import io.arkitik.flotale.plugin.jwt.errors.WebAuthErrors
import io.arkitik.flotale.protocol.user.FlotaleUserTokenData
import io.arkitik.flotale.protocol.user.converter.FlotaleTokenConverter
import io.arkitik.radix.develop.shared.ext.notAuthorized
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.*

/**
 * @author Ibrahim Al-Tamimi 
 * @since 09:19, Friday, 08/05/2026
 **/
internal class JwtFlotaleTokenConverter(
    private val properties: FlotaleJwtProperties,
) : FlotaleTokenConverter {
    companion object {
        private val logger = LoggerFactory.getLogger(JwtFlotaleTokenConverter::class.java)
    }

    private val parser = buildParser()

    override fun convert(token: String): FlotaleUserTokenData {
        val rawToken = token.removePrefix("Bearer ").trim()
        val claims = runCatching {
            parseClaim(rawToken)
        }.onFailure {
            logger.error("Error while parsing JWT token, [Cause by: {}]", it.message, it)
        }.getOrElse {
            throw WebAuthErrors.INVALID_TOKEN_SIGNATURE.notAuthorized()
        }
        if (claims.expiration < Date())
            throw WebAuthErrors.EXPIRED_TOKEN.notAuthorized()

        val mapping = properties.claims

        val userId = claims[mapping.userId]?.toString() ?: claims.subject ?: ""
        val username = claims[mapping.username]?.toString() ?: userId
        val email = claims[mapping.email]?.toString()

        val roles = (claims[mapping.roles] as? Collection<*>)
            ?.mapNotNull { it?.toString() }
            ?.toSet() ?: emptySet()

        val reservedKeys = setOf(
            mapping.userId, mapping.username, mapping.email, mapping.roles,
            "sub", "iss", "aud", "exp", "nbf", "iat", "jti",
        )
        val attributes = claims
            .filterKeys { it !in reservedKeys }
            .mapValues { it.value?.toString() }

        return JwtFlotaleUserTokenData(
            userId = userId,
            username = username,
            email = email,
            roles = roles,
            attributes = attributes,
        )
    }

    private fun parseClaim(rawToken: String): Claims =
        when {
            properties.secret == null && properties.publicKey == null -> {
                parser.parseUnsecuredClaims(rawToken).payload
            }

            else -> {
                parser.parseSignedClaims(rawToken).payload
            }
        }

    private fun buildParser() = when {
        properties.secret != null -> {
            val key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(properties.secret))
            Jwts.parser().verifyWith(key).build()
        }

        properties.publicKey != null -> {
            val publicKey = KeyFactory.getInstance(properties.publicKey.algorithm)
                .generatePublic(
                    X509EncodedKeySpec(
                        Base64.getDecoder().decode(properties.publicKey.content.contentAsByteArray)
                    )
                )
            Jwts.parser().verifyWith(publicKey).build()
        }

        else -> {
            logger.warn("JWT plugin 'flotale.plugin.jwt.secret' (HMAC) or 'flotale.plugin.jwt.public-key' are not configured, using default parser without signature verification. This is not recommended for production environments.")
            Jwts.parser().build()
        }
    }
}