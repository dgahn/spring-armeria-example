package me.dgahn.api

import me.dgahn.application.MemberService
import me.dgahn.entity.Address
import me.dgahn.entity.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class MemberApiController {

    @Autowired
    lateinit var memberService: MemberService

    @GetMapping("api/v1/members")
    fun membersV1(): List<Member> {
        return memberService.findMembers()
    }

    @GetMapping("api/v2/members")
    fun membersV2(): Result<List<MemberDto>> {
        val findMembers = memberService.findMembers()
        val result = findMembers.map { MemberDto(it.name) }
        return Result(data = result)
    }

    @PostMapping("/api/v1/members")
    fun saveMemberV1(@RequestBody @Valid member: Member): CreateMemberResponse {
        val id = memberService.signUp(member)
        return CreateMemberResponse(id)
    }

    @PostMapping("api/v2/members")
    fun saveMembers(@RequestBody @Valid request: CreateMemberRequest): CreateMemberResponse {
        val member = Member(
            name = request.name,
            address = Address("city", "street", "zipcode")
        )

        val id = memberService.signUp(member)
        return CreateMemberResponse(id)
    }

    @PutMapping("api/v2/members/{id}")
    fun updateMemberV2(
        @PathVariable("id") id: Long,
        @RequestBody @Valid request: UpdateMemberRequest
    ): UpdateMemberResponse {
        memberService.update(id, request.name)
        val findMember = memberService.findOne(id)
        return UpdateMemberResponse(findMember.id!!, findMember.name)
    }
}

class CreateMemberResponse(
    val id: Long
)

class CreateMemberRequest(
    val name: String
)

class UpdateMemberRequest(
    val name: String
)

class UpdateMemberResponse(
    val id: Long,
    val name: String
)

class MemberDto(
    val name: String
)

class Result<T>(
    val data: T
)
