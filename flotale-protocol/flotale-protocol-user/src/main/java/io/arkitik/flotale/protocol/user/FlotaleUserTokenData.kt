package io.arkitik.flotale.protocol.user

/**
 * @author Ibrahim Al-Tamimi 
 * @since 08:32, Friday, 08/05/2026
 **/
interface FlotaleUserTokenData {
    val userId: String
    val username: String
    val email: String?
    val roles: Set<String>
    val attributes: Map<String, String?>
        get() = emptyMap()

    companion object {
        data object System : FlotaleUserTokenData {
            override val userId = "SYSTEM"
            override val username = "SYSTEM"
            override val email = null
            override val roles: Set<String> = emptySet()
            override val attributes: Map<String, String?> = emptyMap()
        }

        val system = System
    }
}