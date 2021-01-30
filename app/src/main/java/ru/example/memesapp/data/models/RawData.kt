package ru.example.memesapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(
    @SerialName("result")
    val result: List<RawData>,
    @SerialName("totalCount")
    val total: Int
)

@Serializable
data class RawData(
    @SerialName("id")
    val id: Int,
    @SerialName("gifURL")
    val imageLink: String,
    @SerialName("description")
    val description: String
)