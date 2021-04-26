package me.dgahn.controller

import me.dgahn.dto.MemberDto
import me.dgahn.entity.Member
import me.dgahn.repository.MemberRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct

@RestController
class MemberController(
    private val memberRepository: MemberRepository
) {
    @GetMapping("/members/{id}")
    fun findMember(@PathVariable("id") id: Long): String {
        val member = memberRepository.findById(id).get()
        return member.username
    }

    @GetMapping("/members2/{id}")
    fun findMember2(@PathVariable("id") member: Member): String {
        return member.username
    }

    @GetMapping("/members")
    fun list(@PageableDefault(size = 5) pageable: Pageable): Page<MemberDto> {
        val findAll = memberRepository.findAll(pageable)
        return findAll.map { MemberDto(it.id!!, it.username, "") }
    }

    @PostConstruct
    fun init() {
        for (i in 1..100) {
            memberRepository.save(Member(username = "member1$i", age = i))
        }
    }
}