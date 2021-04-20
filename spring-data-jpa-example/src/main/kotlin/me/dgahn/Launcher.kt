package me.dgahn

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.util.Optional
import java.util.UUID

private val logger = KotlinLogging.logger { }

@SpringBootApplication
@EnableJpaAuditing
open class Launcher {

    @Bean
    fun auditorProvider(): AuditorAware<String> = AuditorAware { Optional.of(UUID.randomUUID().toString()) }
}

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<Launcher>(*args)
}
