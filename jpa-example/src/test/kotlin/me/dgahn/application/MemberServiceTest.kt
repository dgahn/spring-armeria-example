package me.dgahn.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import me.dgahn.entity.Address
import me.dgahn.entity.Member
import me.dgahn.repo.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
open class MemberServiceTest : AnnotationSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun signUpTest() {
        val member = Member(
            name = "name",
            address = Address(
                city = "city",
                street = "street",
                zipCode = "zipCode"
            )
        )

        val savedId = memberService.signUp(member)

        val findMember = memberRepository.findOne(savedId)
        findMember shouldBe member
    }

    @Test
    fun signUpDuplicationTest() {
        val member1 = Member(
            name = "name",
            address = Address(
                city = "city",
                street = "street",
                zipCode = "zipCode"
            )
        )

        val member2 = Member(
            name = "name",
            address = Address(
                city = "city",
                street = "street",
                zipCode = "zipCode"
            )
        )

        memberService.signUp(member1)
        shouldThrow<IllegalStateException> { memberService.signUp(member2) }
    }
}
