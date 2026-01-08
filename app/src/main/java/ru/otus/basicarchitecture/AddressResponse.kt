package ru.otus.basicarchitecture

import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    val suggestions: List<AddressSuggestion>
)

@Serializable
data class AddressSuggestion(
    val value: String
)
