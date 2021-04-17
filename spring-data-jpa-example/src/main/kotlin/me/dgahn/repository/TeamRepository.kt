package me.dgahn.repository

import me.dgahn.entity.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface TeamRepository : JpaRepository<Team, Long>