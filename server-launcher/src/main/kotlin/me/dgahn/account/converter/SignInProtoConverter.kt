package me.dgahn.account.converter

import me.dgahn.account.entity.Account
import me.dgahn.account.message.SignInRequest
import me.dgahn.account.message.SignInResponse
import me.dgahn.account.param.SignInParam

fun Account.toSignInResponse(): SignInResponse = SignInResponse.newBuilder()
    .setEmail(email)
    .build()

fun SignInRequest.toParam() = SignInParam(
    email = email,
    password = password
)
