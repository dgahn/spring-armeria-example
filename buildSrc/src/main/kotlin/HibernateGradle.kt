object HibernateVersion {
    const val p6spy = "1.7.0"
    const val jacksonDataType = "2.12.2"
}

object HibernateLibs {
    const val p6spy = "com.github.gavlyukovskiy:p6spy-spring-boot-starter:${HibernateVersion.p6spy}"
    const val jacksonDataType =
        "com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:${HibernateVersion.jacksonDataType}"
}
