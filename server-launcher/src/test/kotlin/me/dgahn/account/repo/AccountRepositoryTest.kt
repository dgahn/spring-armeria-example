package me.dgahn.account.repo

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import me.dgahn.account.fixture.entity.savedAccount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
open class AccountRepositoryTest : AnnotationSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var accountRepo: AccountRepository

    @Test
    @Transactional
    open fun saveTest() {
        // given
        val account = savedAccount

        // when
        accountRepo.save(account)
        val findAccount = accountRepo.find(account.email)

        // then
        findAccount shouldBe account
    }
}
