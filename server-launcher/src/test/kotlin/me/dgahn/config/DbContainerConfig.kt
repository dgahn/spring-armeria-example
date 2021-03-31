
package me.dgahn.config

import io.kotest.core.config.AbstractProjectConfig
import org.testcontainers.containers.MSSQLServerContainer
import org.testcontainers.utility.DockerImageName

object DbContainerConfig : AbstractProjectConfig() {
    var db: MSSQLServerContainer<*> = MSSQLServerContainer<Nothing>(DockerImageName.parse("mcr.microsoft.com/mssql/server:2019-latest"))
        .acceptLicense()

    override fun beforeAll() {
        db.start()

        System.setProperty("spring.datasource.url", db.jdbcUrl)
        System.setProperty("spring.datasource.username", db.username)
        System.setProperty("spring.datasource.password", db.password)
        System.setProperty("spring.datasource.driver-class-name", db.driverClassName)
    }

    override fun afterAll() {
        db.stop()
    }
}
