package me.dgahn.account

import io.grpc.BindableService
import me.dgahn.account.service.AccountRouteService

fun accountWebServices(): List<BindableService> = listOf(
    AccountRouteService()
)
