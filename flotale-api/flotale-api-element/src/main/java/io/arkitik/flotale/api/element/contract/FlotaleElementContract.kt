package io.arkitik.flotale.api.element.contract

import io.arkitik.flotale.engine.core.dto.ElementAuditEntry
import io.arkitik.flotale.engine.core.dto.ElementDetails
import io.arkitik.flotale.protocol.user.FlotaleUserTokenData
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author Ibrahim Al-Tamimi 
 * @since 08:25, Friday, 08/05/2026
 **/
@RequestMapping("flotale/api/v1/workflow/elements/{elementType}/{elementKey}")
interface FlotaleElementContract {
    @GetMapping
    fun elementDetails(
        @RequestHeader(HttpHeaders.AUTHORIZATION) userData: FlotaleUserTokenData,
        @PathVariable elementKey: String,
        @PathVariable elementType: String,
    ): ResponseEntity<ElementDetails>

    @PostMapping("/actions/{actionKey}")
    fun executeAction(
        @RequestHeader(HttpHeaders.AUTHORIZATION) userData: FlotaleUserTokenData,
        @PathVariable elementType: String,
        @PathVariable elementKey: String,
        @PathVariable actionKey: String,
        @RequestBody(required = false) request: Map<String, Any>?,
    ): ResponseEntity<ElementDetails>

    @GetMapping("/audit")
    fun elementAudit(
        @RequestHeader(HttpHeaders.AUTHORIZATION) userData: FlotaleUserTokenData,
        @PathVariable elementKey: String,
        @PathVariable elementType: String,
        @RequestParam(required = false, defaultValue = "false") ascending: Boolean = false,
    ): ResponseEntity<List<ElementAuditEntry>>
}