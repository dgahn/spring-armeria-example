package me.dgahn.account.param

import me.dgahn.account.entity.AccountRole

data class SignUpParam(
    val email: String,
    val name: String,
    val password: String,
    val role: AccountRole
)

data class SignInParam(
    val email: String,
    val password: String
)

data class UpdateParam(
    val name: String = "",
    val email: String = "",
    val oldPassword: String = "",
    val newPassword: String = "",
    val role: AccountRole = AccountRole.NONE
)

data class GetAllAccountPram(
    private val role: AccountRole
) {
    val roleList: List<AccountRole> = when (role) {
        AccountRole.NONE -> listOf(AccountRole.OPERATOR, AccountRole.USER, AccountRole.ADMIN)
        else -> listOf(role)
    }
}
