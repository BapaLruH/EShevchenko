package ru.example.memesapp.presentation.models

data class ImageItem(
    override val id: Int,
    val image: String,
    val description: String
) : ItemModel
