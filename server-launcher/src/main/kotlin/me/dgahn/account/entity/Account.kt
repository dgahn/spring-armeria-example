package me.dgahn.account.entity

import me.dgahn.account.exception.PasswordInvalidException
import me.dgahn.account.param.UpdateParam
import org.mindrot.jbcrypt.BCrypt
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity
data class Account(
    @Id
    val email: String,
    val name: String,
    val password: String,
    @Enumerated(EnumType.STRING)
    val role: AccountRole,
) {
    fun encryptPassword() = Account(
        email = email,
        password = BCrypt.hashpw(password, BCrypt.gensalt()),
        name = name,
        role = role
    )

    fun checkPassword(plainPassword: String) = BCrypt.checkpw(plainPassword, password)

    fun update(param: UpdateParam): Account {
        val newPassword = if (param.newPassword.isNotBlank() && param.oldPassword.isNotBlank())
            createNewPassword(param.oldPassword, param.newPassword) else this.password
        return Account(
            email = this.email,
            name = if (param.name.isNotBlank()) param.name else this.name,
            password = newPassword,
            role = if (param.role != AccountRole.NONE) param.role else this.role,
        )
    }

    private fun createNewPassword(oldPassword: String, newPassword: String): String =
        if (checkPassword(oldPassword))
            BCrypt.hashpw(newPassword, BCrypt.gensalt())
        else throw PasswordInvalidException()
}
