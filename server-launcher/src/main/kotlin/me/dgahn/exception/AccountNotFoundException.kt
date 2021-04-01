package me.dgahn.exception

class AccountNotFoundException(override val message: String = "") : RuntimeException()
