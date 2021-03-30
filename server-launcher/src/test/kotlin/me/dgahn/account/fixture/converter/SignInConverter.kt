package me.dgahn.account.fixture.converter

import me.dgahn.account.entity.Account
import me.dgahn.account.message.SignInRequest

fun Account.toSignInRequest() = SignInRequest.newBuilder()
    .setEmail(email)
    .setPassword(password)
    .build()
