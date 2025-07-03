package com.example.bookshelf.data

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: String,
    val progress: Int = 0 // percentage
)
