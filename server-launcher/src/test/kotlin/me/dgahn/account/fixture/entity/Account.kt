package me.dgahn.account.fixture.entity

import me.dgahn.account.entity.Account
import me.dgahn.account.entity.AccountRole

val savedAccount = Account(
    email = "123@si-analytics.ai",
    name = "123",
    password = "1234",
    role = AccountRole.ADMIN
)
//
// val account1Fixture = Account(
//    email = "abc@si-analytics.ai",
//    name = "abc",
//    password = "1234",
//    createdDate = TimeProvider.nowForSql(),
//    role = AccountRole.ADMIN
// )
//
// val account2Fixture = Account(
//    email = "abcd@si-analytics.ai",
//    name = "abc",
//    password = "1234",
//    createdDate = TimeProvider.nowForSql(),
//    role = AccountRole.ADMIN
// )
//
// val accountListFixture = listOf(
//    account1Fixture,
//    account2Fixture
// )
