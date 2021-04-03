package me.dgahn.exception

class NotEnoughStockException(
    s: String? = null,
    cause: Throwable? = null
) : RuntimeException(s, cause)
