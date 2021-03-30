package me.dgahn.account.exception

class AccountNotFoundException(override val message: String = "") : RuntimeException()
