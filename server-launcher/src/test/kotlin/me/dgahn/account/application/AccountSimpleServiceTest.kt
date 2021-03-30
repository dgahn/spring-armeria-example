package me.dgahn.account.application

import io.kotest.core.spec.style.FunSpec

class AccountSimpleServiceTest : FunSpec({
//    val simpleService = AccountSimpleService()
//    val accountRepo = AccountHibernateRepository()
//    lateinit var account: Account
//
//    beforeEach {
//        account = accountRepo.save(savedAccount.encryptPassword())
//    }
//
//    afterEach {
//        accountRepo.deleteAll()
//    }
//
//    afterSpec {
//        close(accountRepo)
//    }
//
//    context("SignIn 함수 테스트") {
//        test("비밀번호가 일치하면 로그인이 된다.") {
//            val expected = account
//            val actual = simpleService.signIn(SignInParam(expected.email, savedAccount.password))
//
//            actual shouldBe expected
//        }
//    }
//
//    context("SignUp 함수 테스트") {
//        test("동일한 메일이 없으면 사용자를 저장할 수 있다.") {
//            val expected = account1Fixture
//            val actual = simpleService.signUp(
//                param = SignUpParam(
//                    email = expected.email,
//                    name = expected.name,
//                    password = expected.password,
//                    role = expected.role
//                )
//            )
//            actual.checkPassword(expected.password) shouldBe true
//        }
//
//        test("동일한 메일이 있으면 사용자를 저장할 수 없다.") {
//            val expected = account
//            shouldThrow<EmailDuplicationException> {
//                simpleService.signUp(
//                    param = SignUpParam(
//                        email = expected.email,
//                        name = expected.name,
//                        password = expected.password,
//                        role = expected.role
//                    )
//                )
//            }
//        }
//    }
//
//    context("Update 함수 테스트") {
//        test("사용자를 찾지 못하면 AccountNotFoundException 예외가 발생한다.") {
//            shouldThrow<AccountNotFoundException> { simpleService.update(param = UpdateParam()) }
//        }
//
//        test("사용자를 업데이트할 수 있다.") {
//            val newPassword = "1234555"
//            val newName = "newName"
//            val newRole = AccountRole.USER
//            simpleService.update(
//                param = UpdateParam(
//                    email = account.email,
//                    name = newName,
//                    oldPassword = savedAccount.password,
//                    newPassword = newPassword,
//                    role = newRole
//                )
//            )
//
//            val findAccount = accountRepo.findOne(account.email)
//            findAccount.checkPassword(newPassword) shouldBe true
//            findAccount.email shouldBe account.email
//            findAccount.name shouldBe newName
//            findAccount.role shouldBe newRole
//        }
//    }
})
