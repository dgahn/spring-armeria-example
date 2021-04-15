package me.dgahn

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

private val logger = KotlinLogging.logger { }

@SpringBootApplication
open class Launcher {

    @Bean
    open fun hibernate5Module(): Hibernate5Module {
        val hibernate5Module = Hibernate5Module()
//        hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true)
        return hibernate5Module
    }
}

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<Launcher>(*args)
}
