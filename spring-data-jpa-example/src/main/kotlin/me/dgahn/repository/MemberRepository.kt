package me.dgahn.repository

import me.dgahn.dto.MemberDto
import me.dgahn.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>

    @Query(name = "Member.findByUsername") // 빠져도 됨. 왜냐하면 Repository의 엔티티를 참고해서 사용함.
    fun findByUsername(@Param("username") username: String): List<Member>

    @Query("SELECT m FROM Member m WHERE m.username = :username AND m.age = :age")
    fun findUser(@Param("username") username: String, @Param("age") age: Int): Member

    @Query("SELECT m.username FROM Member m")
    fun findUsernameList(): List<String>

    @Query("SELECT new me.dgahn.dto.MemberDto(m.id, m.username, t.name) FROM Member m join m.team t")
    fun findMemberDto(): List<MemberDto>
}