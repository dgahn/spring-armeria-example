package me.dgahn

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

private val logger = KotlinLogging.logger { }

@SpringBootApplication
open class Launcher

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<Launcher>(*args)
}
