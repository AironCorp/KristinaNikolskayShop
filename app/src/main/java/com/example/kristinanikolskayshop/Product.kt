package com.example.kristinanikolskayshop

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val title: String,
    val imageURL: String
)