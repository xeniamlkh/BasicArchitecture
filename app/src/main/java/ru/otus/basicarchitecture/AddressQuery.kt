package ru.otus.basicarchitecture

import kotlinx.serialization.Serializable

@Serializable
data class AddressQuery(
    val query: String
)
