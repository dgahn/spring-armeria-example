package me.dgahn.repository

import me.dgahn.entity.Member
import me.dgahn.entity.Team
import org.springframework.data.jpa.domain.Specification
import org.springframework.util.StringUtils
import javax.persistence.criteria.Join
import javax.persistence.criteria.JoinType

fun teamName(teamName: String): Specification<Member> = Specification<Member> { root, _, builder ->
    if (StringUtils.hasLength(teamName)) {
        null
    } else {
        val t: Join<Member, Team> = root.join("team", JoinType.INNER)
        builder.equal(t.get<Team>("name"), teamName)
    }
}

fun username(username: String): Specification<Member> = Specification<Member> { root, _, builder ->
    builder.equal(root.get<Member>("username"), username)
}
