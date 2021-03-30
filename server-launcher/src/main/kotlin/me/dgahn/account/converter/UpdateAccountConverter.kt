package me.dgahn.account.converter

import me.dgahn.account.entity.Account
import me.dgahn.account.message.UpdateAccountRequest
import me.dgahn.account.message.UpdateAccountResponse
import me.dgahn.account.param.UpdateParam

fun UpdateAccountRequest.toParam() = UpdateParam(
    email = email,
    name = name,
    oldPassword = password,
    newPassword = newPassword,
    role = role.toEntity(),
)

fun Account.toUpdateAccountResponse(): UpdateAccountResponse = UpdateAccountResponse.newBuilder()
    .setName(name)
    .setEmail(email)
    .setRole(role.toProto())
    .build()
