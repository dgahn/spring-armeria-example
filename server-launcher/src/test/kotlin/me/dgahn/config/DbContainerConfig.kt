
package me.dgahn.config

import io.kotest.core.config.AbstractProjectConfig
import org.testcontainers.containers.MSSQLServerContainer
import org.testcontainers.utility.DockerImageName

object DbContainerConfig : AbstractProjectConfig() {
    var db: MSSQLServerContainer<*> = MSSQLServerContainer<Nothing>(DockerImageName.parse("mcr.microsoft.com/mssql/server:2019-latest")).acceptLicense()

    override fun beforeAll() {
        db.start()
    }

    override fun afterAll() {
        db.stop()
    }
}
