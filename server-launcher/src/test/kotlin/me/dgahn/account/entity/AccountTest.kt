package me.dgahn.account.entity

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import me.dgahn.account.exception.PasswordInvalidException
import me.dgahn.account.fixture.entity.savedAccount
import me.dgahn.account.param.UpdateParam

class AccountTest : FunSpec({

    val plainPasswordAccount = savedAccount
    val encryptPasswordAccount = plainPasswordAccount.encryptPassword()

    context("update 함수 테스트") {
        test("param이 기본 값이면 업데이트를 하지 않는다.") {
            val param = UpdateParam()
            val actual = plainPasswordAccount.update(param)
            actual shouldBe plainPasswordAccount
        }

        test("param에 newPassword와 oldPassword가 있고 oldPassword가 맞으면 새로운 비밀번호 업데이트 한다.") {
            val oldPassword = plainPasswordAccount.password
            val newPassword = "12345"
            val param = UpdateParam(newPassword = newPassword, oldPassword = oldPassword)
            val actual = encryptPasswordAccount.update(param)
            actual.checkPassword(newPassword) shouldBe true
        }

        test("param에 newPassword와 oldPassword가 있는데 oldPassword가 틀리면 PasswordInvalidException이 발생한다.") {
            val newPassword = "12345"
            val invalidPassword = "123411"
            val param = UpdateParam(newPassword = newPassword, oldPassword = invalidPassword)
            shouldThrow<PasswordInvalidException> { encryptPasswordAccount.update(param) }
        }

        test("param에 name이 있으면 name을 업데이트 한다.") {
            val newName = "yayay"
            val param = UpdateParam(name = newName)
            val actual = encryptPasswordAccount.update(param)
            actual shouldBe encryptPasswordAccount.copy(name = newName)
        }

        test("param에 role이 있으면 role을 업데이트 한다.") {
            val newRole = AccountRole.OPERATOR
            val param = UpdateParam(role = newRole)
            val actual = encryptPasswordAccount.update(param)
            actual shouldBe encryptPasswordAccount.copy(role = newRole)
        }

        test("param의 role이 NONE이면 업데이트하지 않는다.") {
            val newRole = AccountRole.NONE
            val param = UpdateParam(role = newRole)
            val actual = encryptPasswordAccount.update(param)
            actual.role shouldNotBe newRole
        }
    }
})
