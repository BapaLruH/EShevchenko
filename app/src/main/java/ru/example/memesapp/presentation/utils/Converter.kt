package ru.example.memesapp.presentation.utils

import ru.example.memesapp.data.models.RawData
import ru.example.memesapp.presentation.models.ImageItem

fun RawData.toImageItem() : ImageItem {
    return ImageItem(id, imageLink, description)
}
