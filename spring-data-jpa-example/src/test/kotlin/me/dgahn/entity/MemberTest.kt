package me.dgahn.entity

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import me.dgahn.repository.MemberRepository
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import javax.persistence.EntityManager
import javax.transaction.Transactional

@SpringBootTest
@Transactional
@Rollback(false)
open class MemberTest(
    private val em: EntityManager,
    private val memberRepository: MemberRepository
) : AnnotationSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Test
    fun `엔티티 테스트`() {
        val teamA = Team(name = "teamA")
        val teamB = Team(name = "teamB")
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member(username = "member1", age = 10, team = teamA)
        val member2 = Member(username = "member2", age = 10, team = teamA)
        val member3 = Member(username = "member3", age = 10, team = teamB)
        val member4 = Member(username = "member4", age = 10, team = teamB)

        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        // 실제로 데이터베이스 넣음.
        em.flush()
        em.clear()

        val result = em.createQuery("SELECT m FROM Member m", Member::class.java)
            .resultList

        result.forEach {
            println("member = $it")
            println("-> member.team = ${it.team}")
        }
    }

    @Test
    fun `JpaBaseEntity 테스트`() {
        try {

            val member1 = Member(username = "member1", age = 10)
            memberRepository.save(member1)
            member1.username = "member2"

            em.flush()
            em.clear()

            val findMember = memberRepository.findById(member1.id!!)

            val get = findMember.get()
            get.createdDate shouldNotBe null
            get.lastModifiedDate!! shouldBeAfter get.createdDate!!
            get.createdBy shouldNotBe null
            get.lastModifiedBy shouldNotBe null
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}