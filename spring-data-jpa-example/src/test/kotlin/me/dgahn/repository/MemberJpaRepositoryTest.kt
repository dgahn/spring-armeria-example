package me.dgahn.repository

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import me.dgahn.entity.Member
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import javax.transaction.Transactional

@SpringBootTest
@Transactional
@Rollback(false)
open class MemberJpaRepositoryTest(
    private val memberJpaRepository: MemberJpaRepository
) : AnnotationSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Test
    fun `구성원을 저장할 수 있다`() {
        val member = Member(username = "구성원1", age = 10)
        val savedMember = memberJpaRepository.save(member)

        val findMember = memberJpaRepository.find(savedMember.id!!)
        findMember shouldBe savedMember
    }

    @Test
    fun `구성원에 대한 CRUD 테스트`() {
        val member1 = Member(username = "member1", age = 10)
        val member2 = Member(username = "member2", age = 12)

        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        val findMember1 = memberJpaRepository.findById(member1.id!!)
        val findMember2 = memberJpaRepository.findById(member2.id!!)

        findMember1 shouldBe member1
        findMember2 shouldBe member2

        val members = memberJpaRepository.findAll()
        members shouldHaveSize 2

        val count = memberJpaRepository.count()
        count shouldBe 2

        memberJpaRepository.delete(member1)
        memberJpaRepository.delete(member2)
        val deletedCount = memberJpaRepository.count()
        deletedCount shouldBe 0
    }

    @Test
    fun `사용자 이름과 나이로 사용자를 조회할 수 있다`() {
        val m1 = Member(username = "AAA", age = 10)
        val m2 = Member(username = "AAA", age = 20)
        memberJpaRepository.save(m1)
        memberJpaRepository.save(m2)

        val findMember = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15)

        findMember shouldContain m2
        findMember shouldNotContain m1
    }
}