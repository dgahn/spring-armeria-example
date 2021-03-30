package me.dgahn.account.service

// class AccountRouteServiceTest : FunSpec({
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
//    val service = AccountRouteService()
//
//    context("SignIn 함수 테스트") {
//        test("비밀번호가 일치하면 로그인이 된다.") {
//            val request = savedAccount.toSignInRequest()
//            val actual = service.signIn(request)
//
//            actual shouldBe savedAccount.toSignInResponse()
//        }
//    }
//
//    context("SignUp 함수 테스트") {
//        test("동일한 메일이 없으면 사용자를 저장할 수 있다.") {
//            val expected = account1Fixture
//            val actual = service.signUp(expected.toSignUpRequest())
//            actual shouldBe expected.toSignUpResponse()
//        }
//
//        test("동일한 메일이 있으면 사용자를 저장할 수 없다.") {
//            shouldThrow<StatusException> { service.signUp(account.toSignUpRequest()) }
//        }
//    }
//
//    context("updateAccount 함수 테스트") {
//        test("사용자를 업데이트 할 수 있다.") {
//            val email = account.email
//            val newPassword = "1234555"
//            val newName = "newName"
//            val newRole = AccountRole.USER
//
//            val request = UpdateAccountRequest.newBuilder()
//                .setEmail(email)
//                .setName(newName)
//                .setNewPassword(newPassword)
//                .setPassword(savedAccount.password)
//                .setRole(newRole.toProto())
//                .build()
//
//            val updateAccount = service.updateAccount(request)
//            updateAccount.email shouldBe email
//            updateAccount.name shouldBe newName
//            updateAccount.role.toEntity() shouldBe newRole
//        }
//    }
// })
