package me.dgahn.repository

import me.dgahn.entity.Member

interface MemberRepositoryCustom {

    fun findMemberCustom(): List<Member>

}