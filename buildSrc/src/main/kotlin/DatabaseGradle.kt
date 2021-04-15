object DatabasePlugin {
    const val queryDsl = "com.ewerk.gradle.plugins.querydsl"
}

object DatabaseVersion {
    const val mssql = "9.2.1.jre11"
    const val queryDsl = "1.0.10"
    const val h2 = "1.4.200"
}

object DatabaseLibs {
    const val mssql = "com.microsoft.sqlserver:mssql-jdbc:${DatabaseVersion.mssql}"
    const val querydslJpa = "com.querydsl:querydsl-jpa"
    const val querydslApt = "com.querydsl:querydsl-apt"
    const val h2 = "com.h2database:h2:${DatabaseVersion.h2}"
}