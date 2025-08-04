package io.arkitik.flotale.api.authenticated.contract

import io.arkitik.flotale.api.authenticated.dtos.FlotaleUserData
import io.arkitik.flotale.engine.core.dto.ElementDetails
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created By Ibrahim Al-Tamimi 
 * Created At 9:32 PM, 03/08/2025
 */
@RequestMapping("/flotale/api/v1/engine")
interface FlotaleAuthenticatedContract {
    @PostMapping("elements/{elementKey}/actions/{actionKey}/execute")
    fun executeAction(
        @PathVariable("elementKey") elementKey: String,
        @PathVariable("actionKey") actionKey: String,
        @RequestHeader(HttpHeaders.AUTHORIZATION) userData: FlotaleUserData,
    )

    @GetMapping("/elements/{elementKey}")
    fun elementDetails(
        @PathVariable("elementKey") elementKey: String,
        @RequestHeader(HttpHeaders.AUTHORIZATION) userData: FlotaleUserData,
    ): ResponseEntity<ElementDetails>
}