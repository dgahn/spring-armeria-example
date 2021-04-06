package me.dgahn.application

import me.dgahn.entity.Member
import me.dgahn.repo.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    @Autowired
    val memberRepo: MemberRepository
) {

    /**
     * 회원 가입
     */

    @Transactional
    fun signUp(member: Member): Long {
        validateDuplicateMember(member)
        memberRepo.save(member)
        return member.id!!
    }

    private fun validateDuplicateMember(member: Member) {
        val findMembers = memberRepo.findByName(member.name)
        if (findMembers.isNotEmpty()) {
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }

    /**
     * 회원 전체 조회
     */

    fun findMembers(): List<Member> {
        return memberRepo.findAll()
    }

    fun findOne(memberId: Long) = memberRepo.findOne(memberId)

    @Transactional // 커맨드와 쿼리를 분리한다?
    fun update(id: Long, name: String) {
        val member = memberRepo.findOne(id)
        member.name = name
    }
}
