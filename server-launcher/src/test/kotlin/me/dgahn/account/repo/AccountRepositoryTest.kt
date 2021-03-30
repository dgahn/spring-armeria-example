package me.dgahn.account.repo

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import me.dgahn.account.fixture.entity.savedAccount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountRepositoryTest : FunSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var accountRepo: AccountRepository

    init {
        test("account을 저장할 수 있다.") {
            // given
            val account = savedAccount

            // when
            accountRepo.save(account)
            val findAccount = accountRepo.find(account.email)

            // then
            findAccount shouldBe account
        }
    }
}
