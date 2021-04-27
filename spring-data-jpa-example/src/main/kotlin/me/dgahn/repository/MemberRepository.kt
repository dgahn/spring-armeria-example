package me.dgahn.repository

import me.dgahn.dto.MemberDto
import me.dgahn.entity.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.query.Param
import java.util.Optional
import javax.persistence.LockModeType
import javax.persistence.QueryHint

interface MemberRepository : JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member> {

    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>

    @Query(name = "Member.findByUsername") // 빠져도 됨. 왜냐하면 Repository의 엔티티를 참고해서 사용함.
    fun findByUsername(@Param("username") username: String): List<Member>

    @Query("SELECT m FROM Member m WHERE m.username = :username AND m.age = :age")
    fun findUser(@Param("username") username: String, @Param("age") age: Int): Member

    @Query("SELECT m.username FROM Member m")
    fun findUsernameList(): List<String>

    @Query("SELECT new me.dgahn.dto.MemberDto(m.id, m.username, t.name) FROM Member m join m.team t")
    fun findMemberDto(): List<MemberDto>

    @Query("SELECT m FROM Member m WHERE m.username IN :names")
    fun findByNames(@Param("names") names: Collection<String>): List<Member>

    fun findListByUsername(username: String): List<Member> // 컬렉션

    fun findMemberByUsername(username: String): Member? // 단건

    fun findOptionalByUsername(username: String): Optional<Member>

    @Query(value = "SELECT m FROM Member m LEFT JOIN m.team t", countQuery = "SELECT count(m.username) FROM Member m")
    fun findByAge(age: Int, pageable: Pageable): Page<Member>

    fun findSliceByAge(age: Int, pageable: Pageable): Slice<Member>

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m set m.age = m.age + 1 WHERE m.age >= :age")
    fun bulkAgePlus(@Param("age") age: Int): Int

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.team")
    fun findMemberFetchJoin(): List<Member>

    @EntityGraph(attributePaths = ["team"])
    override fun findAll(): List<Member>

    @EntityGraph(attributePaths = ["team"])
    @Query("SELECT m FROM Member m")
    fun findMemberEntityGraph(): List<Member>

    @EntityGraph(attributePaths = ["team"])
    fun findEntityGraphByUsername(@Param("username") username: String)

    @QueryHints(QueryHint(name = "org.hibernate.readOnly", value = "true"))
    fun findReadOnlyByUsername(username: String): Member

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findLockByUsername(username: String)

    fun <T> findProjectionByUsername(@Param("username") username: String, type: Class<T>): List<T>
}