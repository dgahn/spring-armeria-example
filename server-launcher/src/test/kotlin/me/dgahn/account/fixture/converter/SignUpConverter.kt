package me.dgahn.account.fixture.converter

import me.dgahn.account.converter.toProto
import me.dgahn.account.entity.Account
import me.dgahn.account.message.SignUpRequest

fun Account.toSignUpRequest() = SignUpRequest.newBuilder()
    .setEmail(email)
    .setName(name)
    .setPassword(password)
    .setRole(role.toProto())
    .build()
