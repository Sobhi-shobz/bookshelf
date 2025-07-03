package com.example.bookshelf.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bookshelf.data.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookViewModel : ViewModel() {
    private var _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private var nextId = 1

    fun addBook(title: String, author: String, genre: String) {
        val newBook = Book(nextId++, title, author, genre)
        _books.value = _books.value + newBook
    }

    fun deleteBook(id: Int) {
        _books.value = _books.value.filter { it.id != id }
    }

    fun updateProgress(id: Int, progress: Int) {
        _books.value = _books.value.map {
            if (it.id == id) it.copy(progress = progress) else it
        }
    }

    fun searchBooks(query: String): List<Book> {
        return _books.value.filter {
            it.title.contains(query, true) || it.author.contains(query, true)
        }
    }

    fun sortBooksByGenre(): List<Book> = _books.value.sortedBy { it.genre }
    fun sortBooksByAuthor(): List<Book> = _books.value.sortedBy { it.author }
}
