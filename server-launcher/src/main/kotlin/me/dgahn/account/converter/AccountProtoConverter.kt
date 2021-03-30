package me.dgahn.account.converter

import me.dgahn.account.entity.Account
import me.dgahn.account.entity.AccountRole
import me.dgahn.account.message.AccountResponseProto
import me.dgahn.account.message.AccountRoleProto

fun Account.toProto(): AccountResponseProto = AccountResponseProto.newBuilder()
    .setEmail(email)
    .setName(name)
    .setRole(role.toProto())
    .build()

fun AccountRole.toProto() = when (this) {
    AccountRole.ADMIN -> AccountRoleProto.ADMIN
    AccountRole.USER -> AccountRoleProto.USER
    AccountRole.OPERATOR -> AccountRoleProto.OPERATOR
    else -> throw IllegalArgumentException("This type does not exist in AccountRoleProto ($this) ")
}

fun AccountRoleProto.toEntity(): AccountRole = when (this) {
    AccountRoleProto.NONE -> AccountRole.NONE
    AccountRoleProto.ADMIN -> AccountRole.ADMIN
    AccountRoleProto.USER -> AccountRole.USER
    AccountRoleProto.OPERATOR -> AccountRole.OPERATOR
    else -> throw IllegalArgumentException("This type does not exist in AccountRole ($this) ")
}
