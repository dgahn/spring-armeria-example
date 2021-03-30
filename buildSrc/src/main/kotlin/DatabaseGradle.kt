object DatabaseVersion {
    const val mssql = "9.2.1.jre11"
}

object DatabaseLibs {
    const val mssql = "com.microsoft.sqlserver:mssql-jdbc:${DatabaseVersion.mssql}"
}