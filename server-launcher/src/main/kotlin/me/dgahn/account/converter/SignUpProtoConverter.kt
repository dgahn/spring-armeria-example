package me.dgahn.account.converter

import me.dgahn.account.entity.Account
import me.dgahn.account.message.SignUpRequest
import me.dgahn.account.message.SignUpResponse
import me.dgahn.account.param.SignUpParam

fun SignUpRequest.toParam() = SignUpParam(
    email = email,
    name = name,
    password = password,
    role = role.toEntity()
)

fun Account.toSignUpResponse() = SignUpResponse.newBuilder()
    .setName(name)
    .setEmail(email)
    .setRole(role.toProto())
    .build()
