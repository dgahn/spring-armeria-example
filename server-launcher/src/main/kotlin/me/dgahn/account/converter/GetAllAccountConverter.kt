package me.dgahn.account.converter

import me.dgahn.account.entity.Account
import me.dgahn.account.message.GetAllAccountByRequest
import me.dgahn.account.message.GetAllAccountByResponse
import me.dgahn.account.param.GetAllAccountPram

fun GetAllAccountByRequest.toParam() = GetAllAccountPram(
    role = role.toEntity()
)

fun List<Account>.toGetAllAccountResponse(): GetAllAccountByResponse = GetAllAccountByResponse.newBuilder()
    .addAllAccount(this.map { it.toProto() })
    .build()
