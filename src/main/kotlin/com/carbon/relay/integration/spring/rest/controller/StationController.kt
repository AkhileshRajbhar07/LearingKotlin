package com.carbon.relay.integration.spring.rest.controller

import com.carbon.relay.integration.domains.station.usecase.StationService
import com.carbon.relay.integration.spring.rest.request.StationRegisterRequest
import com.carbon.relay.integration.spring.rest.request.ProvisionRequest
import com.carbon.relay.integration.spring.rest.response.*
import com.carbon.relay.integration.utils.constant.APIPathConstant
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Station", description = "Station registration, validation, provisioning, and data APIs")
@RestController
@RequestMapping(APIPathConstant.API_V1 + APIPathConstant.STATIONS)
class StationController(private val stationService: StationService) {

    @Operation(summary = "Register a station and its connectors in Qualicharge")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Station registration result")
    ])
    @PostMapping("/{stationId}" + APIPathConstant.REGISTER)
    fun registerStation(
        @PathVariable stationId: String,
        @RequestBody request: StationRegisterRequest
    ): ResponseEntity<StationRegisterResponse> {
        val response = stationService.registerStation(stationId, request)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "Validate if a station is ready for registration")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Registration readiness status")
    ])
    @GetMapping("/{stationId}" + APIPathConstant.REGISTRATION_CHECK)
    fun checkRegistration(
        @PathVariable stationId: String
    ): ResponseEntity<StationRegistrationCheckResponse> {
        val response = stationService.checkRegistration(stationId)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "Manually provision tenant, company, station, and connectors")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Provisioning result")
    ])
    @PostMapping(APIPathConstant.PROVISION)
    fun provision(
        @RequestBody request: ProvisionRequest
    ): ResponseEntity<ProvisionResponse> {
        val response = stationService.provision(request)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "Retrieve consolidated station, company, tenant, and connector data")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Station consolidated data")
    ])
    @GetMapping("/{stationId}" + APIPathConstant.DATA)
    fun getStationData(
        @PathVariable stationId: String
    ): ResponseEntity<StationDataResponse> {
        val response = stationService.getStationData(stationId)
        return ResponseEntity.ok(response)
    }
}
