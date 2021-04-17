package me.dgahn.repository

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import me.dgahn.entity.Member
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import javax.transaction.Transactional

@SpringBootTest
@Transactional
@Rollback(false)
open class MemberRepositoryTest(
    private val memberRepository: MemberRepository
) : AnnotationSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Test
    fun `구성원을 저장할 수 있다`() {
        val member = Member(username = "구성원1", age = 11)
        val savedMember = memberRepository.save(member)

        val findMember = memberRepository.findById(savedMember.id!!).get()
        findMember shouldBe savedMember
    }

    @Test
    fun `구성원에 대한 CRUD 테스트`() {
        val member1 = Member(username = "member1", age = 10)
        val member2 = Member(username = "member2", age = 12)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val findMember1 = memberRepository.findById(member1.id!!)
        val findMember2 = memberRepository.findById(member2.id!!)

        findMember1.get() shouldBe member1
        findMember2.get() shouldBe member2

        val members = memberRepository.findAll()
        members shouldHaveSize 2

        val count = memberRepository.count()
        count shouldBe 2

        memberRepository.delete(member1)
        memberRepository.delete(member2)
        val deletedCount = memberRepository.count()
        deletedCount shouldBe 0
    }
}