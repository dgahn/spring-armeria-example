package me.dgahn.repository

import me.dgahn.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface TeamRepository: JpaRepository<Member, Long>