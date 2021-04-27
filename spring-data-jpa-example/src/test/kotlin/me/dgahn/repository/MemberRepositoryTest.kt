package me.dgahn.repository

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import me.dgahn.dto.MemberDto
import me.dgahn.entity.Member
import me.dgahn.entity.Team
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.test.annotation.Rollback
import java.util.Optional
import javax.persistence.EntityManager
import javax.transaction.Transactional

@SpringBootTest
@Transactional
@Rollback(false)
open class MemberRepositoryTest(
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository,
    private val em: EntityManager
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

    @Test
    fun `사용자 이름과 나이로 사용자를 조회할 수 있다`() {
        val m1 = Member(username = "AAA", age = 10)
        val m2 = Member(username = "AAA", age = 20)
        memberRepository.save(m1)
        memberRepository.save(m2)

        val findMember = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15)

        findMember shouldContain m2
        findMember shouldNotContain m1
    }

    @Test
    fun `NamedQuery 테스트`() {
        val m1 = Member(username = "AAA", age = 10)
        val m2 = Member(username = "AAA", age = 20)
        memberRepository.save(m1)
        memberRepository.save(m2)

        val findMembers = memberRepository.findByUsername("AAA")

        findMembers.first() shouldBe m1
    }

    @Test
    fun `Query 테스트`() {
        val m1 = Member(username = "AAA", age = 10)
        val m2 = Member(username = "AAA", age = 20)
        memberRepository.save(m1)
        memberRepository.save(m2)

        val findMembers = memberRepository.findUser("AAA", 10)

        findMembers shouldBe m1
    }

    @Test
    fun `findUsername 목록 테스트`() {
        val m1 = Member(username = "AAA", age = 10)
        val m2 = Member(username = "AAA", age = 20)
        memberRepository.save(m1)
        memberRepository.save(m2)

        val findMembers = memberRepository.findUsernameList()

        findMembers shouldBe listOf("AAA", "AAA")
    }

    @Test
    fun `findMemberDto 테스트`() {
        val team = Team(name = "teamA")
        teamRepository.save(team)
        val m1 = Member(username = "AAA", age = 10)
        m1.changeTeam(team)
        memberRepository.save(m1)

        val findMemberDto = memberRepository.findMemberDto()
        findMemberDto.forEach {
            it.teamName shouldBe "teamA"
            it.username shouldBe "AAA"
        }
    }

    @Test
    fun `findByNameds 테스트`() {
        val m1 = Member(username = "AAA", age = 10)
        val m2 = Member(username = "BBB", age = 20)
        memberRepository.save(m1)
        memberRepository.save(m2)

        val findMembers = memberRepository.findByNames(listOf("AAA", "BBB"))

        findMembers shouldBe listOf(m1, m2)
    }


    @Test
    fun `리턴 타입 테스트`() {
        val m1 = Member(username = "AAA", age = 10)
        val m2 = Member(username = "BBB", age = 20)
        memberRepository.save(m1)
        memberRepository.save(m2)

        val findMembers = memberRepository.findListByUsername("AAA")
        findMembers shouldContain m1

        val findMember = memberRepository.findMemberByUsername("AAA")
        findMember shouldBe m1

        val findNullMember = memberRepository.findMemberByUsername("abc")
        findNullMember shouldBe null

        val findOptionalMember = memberRepository.findOptionalByUsername("AAA")
        findOptionalMember shouldBe Optional.of(m1)
    }

    /**
     * 나이가 10살
     * 이름으로 내림차순
     * 첫 번째 페이지, 페이지당 보여줄 데이터는 3건
     */

    @Test
    fun `paging 테스트`() {
        memberRepository.save(Member(username = "member1", age = 10))
        memberRepository.save(Member(username = "member2", age = 10))
        memberRepository.save(Member(username = "member3", age = 10))
        memberRepository.save(Member(username = "member4", age = 10))
        memberRepository.save(Member(username = "member5", age = 10))

        val age = 10
        val pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"))

        val page = memberRepository.findByAge(age, pageRequest)

        page.content shouldHaveSize 3
        page.totalElements shouldBe 5
        page.number shouldBe 0
        page.totalPages shouldBe 2
        page.isFirst shouldBe true
        page.hasNext() shouldBe true
        // page에 map이 존재함.
        page.map { MemberDto(id = it.id!!, username = it.username!!, teamName = "") }

        val slice: Slice<Member> = memberRepository.findSliceByAge(age, pageRequest)

        slice.content shouldHaveSize 3
        slice.number shouldBe 0
        slice.isFirst shouldBe true
        slice.hasNext() shouldBe true
    }

    @Test
    fun `bulkUpdate 테스트`() {
        memberRepository.save(Member(username = "member1", age = 10))
        memberRepository.save(Member(username = "member2", age = 20))
        memberRepository.save(Member(username = "member3", age = 30))
        memberRepository.save(Member(username = "member4", age = 40))
        memberRepository.save(Member(username = "member5", age = 50))

        val result = memberRepository.bulkAgePlus(20)
        // 남아있는 연산을 적용
//        em.flush()
        // 말그대로 초기화
//        em.clear()

        val findMembers = memberRepository.findByUsername("member5")

        findMembers.first().age shouldBe 51

        result shouldBe 4
    }

    @Test
    fun `게으른 멤버 찾기`() {
        val teamA = Team(name = "teamA")
        val teamB = Team(name = "teamB")
        teamRepository.save(teamA)
        teamRepository.save(teamB)
        val member1 = Member(username = "member1", age = 10, team = teamA)
        val member2 = Member(username = "member1", age = 10, team = teamB)
        memberRepository.save(member1)
        memberRepository.save(member2)

        em.flush()
        em.clear()

        val members = memberRepository.findMemberFetchJoin()
        val findMembers = memberRepository.findAll()

        members shouldBe findMembers
    }

    @Test
    fun `쿼리 힌트`() {
        val savedMember = memberRepository.save(Member(username = "member1", age = 10))
        em.flush()
        em.clear()

        val findMember = memberRepository.findById(savedMember.id!!)
        findMember.get().username = "member2"
        em.flush()
        em.clear()
        val findMember1 = memberRepository.findById(savedMember.id!!)
        findMember1.get().username shouldBe "member2"

        em.flush()
        em.clear()

        val findMember2 = memberRepository.findReadOnlyByUsername("member2")
        findMember2.username = "member1"

        em.flush()
        em.clear()

        val findMember3 = memberRepository.findById(savedMember.id!!)
        findMember3.get().username shouldBe "member2"
        findMember3.get().username shouldNotBe "member1"
    }

    @Test
    fun `Lock 테스트`() {
        val savedMember = memberRepository.save(Member(username = "member1", age = 10))
        em.flush()
        em.clear()

        val findMember = memberRepository.findLockByUsername("member1")
    }

    @Test
    fun `callCustom`() {
        memberRepository.findMemberCustom()
    }

    @Test
    fun `specBasic`() {
        // given
        val teamA = Team(name = "teamA");
        em.persist(teamA);

        val m1 = Member(username = "m1", 0, teamA)
        val m2 = Member(username = "m2", 0, teamA)
        em.persist(m1)
        em.persist(m2)

        em.flush()
        em.clear()
        // when
        val spec = username("m1").and(teamName("teamA"))
        val member = memberRepository.findAll(spec)
        member shouldHaveSize 1
    }

    @Test
    fun queryByExample() {
        val teamA = Team(name = "teamA");
        em.persist(teamA);

        val m1 = Member(username = "m1", 0, teamA)
        val m2 = Member(username = "m2", 0, teamA)
        em.persist(m1)
        em.persist(m2)

        em.flush()
        em.clear()

        val team = Team(name = "teamA")
        val member = Member(username = "m1", team = team)
        val matcher = ExampleMatcher.matching().withIgnorePaths("age");
        val example = Example.of(member, matcher)

        val result = memberRepository.findAll(example)

        result.first().username shouldBe "m1"
    }

    @Test
    fun projections() {
        val teamA = Team(name = "teamA");
        em.persist(teamA);

        val m1 = Member(username = "m1", 0, teamA)
        val m2 = Member(username = "m2", 0, teamA)
        em.persist(m1)
        em.persist(m2)

        em.flush()
        em.clear()

        val findProjectionByUsername = memberRepository.findProjectionByUsername("m1", UsernameOnlyDto::class.java)
        findProjectionByUsername.first().username shouldBe "m1"

        em.flush()
        em.clear()
        val findProjectionByUsername1 =
            memberRepository.findProjectionByUsername("m1", NestedClosedProjections::class.java)
        findProjectionByUsername1.first().getUsername() shouldBe "m1"
    }

    @Test
    fun nativeQuery() {
        val teamA = Team(name = "teamA");
        em.persist(teamA);

        val m1 = Member(username = "m1", 0, teamA)
        val m2 = Member(username = "m2", 0, teamA)
        em.persist(m1)
        em.persist(m2)

        em.flush()
        em.clear()

        val result = memberRepository.findByNativeQuery("m1")
        result.username shouldBe "m1"
    }

}
