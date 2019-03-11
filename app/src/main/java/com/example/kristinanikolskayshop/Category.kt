package com.example.kristinanikolskayshop

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val productURL: String,
    val title: String,
    val imageURL: String
)