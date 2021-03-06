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

    @Test
    fun `NamedQuery 테스트`() {
        val m1 = Member(username = "AAA", age = 10)
        val m2 = Member(username = "AAA", age = 20)
        memberJpaRepository.save(m1)
        memberJpaRepository.save(m2)

        val findMembers = memberJpaRepository.findByUsername("AAA")

        findMembers.first() shouldBe m1
    }

    /**
     * 나이가 10살
     * 이름으로 내림차순
     * 첫 번째 페이지, 페이지당 보여줄 데이터는 3건
     */

    @Test
    fun `paging 테스트`() {
        memberJpaRepository.save(Member(username = "member1", age = 10))
        memberJpaRepository.save(Member(username = "member2", age = 10))
        memberJpaRepository.save(Member(username = "member3", age = 10))
        memberJpaRepository.save(Member(username = "member4", age = 10))
        memberJpaRepository.save(Member(username = "member5", age = 10))

        val age = 10
        val offset = 0
        val limit = 3

        val members = memberJpaRepository.findByPage(age, offset, limit)
        val count = memberJpaRepository.totalCount(age)

        members shouldHaveSize 3
        count shouldBe 5
    }

    @Test
    fun `bulkUpdate 테스트`() {
        memberJpaRepository.save(Member(username = "member1", age = 10))
        memberJpaRepository.save(Member(username = "member2", age = 20))
        memberJpaRepository.save(Member(username = "member3", age = 30))
        memberJpaRepository.save(Member(username = "member4", age = 40))
        memberJpaRepository.save(Member(username = "member5", age = 50))

        val result = memberJpaRepository.bulkAgePlus(20)

        result shouldBe 4
    }
}