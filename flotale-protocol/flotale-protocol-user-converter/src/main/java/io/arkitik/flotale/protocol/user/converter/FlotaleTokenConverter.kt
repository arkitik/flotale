package io.arkitik.flotale.protocol.user.converter

import io.arkitik.flotale.protocol.user.FlotaleUserTokenData

/**
 * @author Ibrahim Al-Tamimi 
 * @since 09:19, Friday, 08/05/2026
 **/
fun interface FlotaleTokenConverter {
    fun convert(token: String): FlotaleUserTokenData
}
