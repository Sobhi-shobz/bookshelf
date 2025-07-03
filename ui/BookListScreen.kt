package com.example.bookshelf.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookshelf.ui.components.BookCard
import com.example.bookshelf.viewmodel.BookViewModel

@Composable
fun BookListScreen(viewModel: BookViewModel) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var query by remember { mutableStateOf("") }
    var sortMode by remember { mutableStateOf("None") }

    val books by viewModel.books.collectAsState()

    val displayedBooks = when {
        query.isNotBlank() -> viewModel.searchBooks(query)
        sortMode == "Genre" -> viewModel.sortBooksByGenre()
        sortMode == "Author" -> viewModel.sortBooksByAuthor()
        else -> books
    }

    Column(Modifier.padding(16.dp)) {
        Text("Add a Book", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        OutlinedTextField(value = author, onValueChange = { author = it }, label = { Text("Author") })
        OutlinedTextField(value = genre, onValueChange = { genre = it }, label = { Text("Genre") })
        Button(onClick = {
            if (title.isNotBlank() && author.isNotBlank()) {
                viewModel.addBook(title, author, genre)
                title = author = genre = ""
            }
        }, modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Add Book")
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Search") })

        Row(Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { sortMode = "Genre" }) { Text("Sort by Genre") }
            Button(onClick = { sortMode = "Author" }) { Text("Sort by Author") }
            Button(onClick = { sortMode = "None" }) { Text("Clear Sort") }
        }

        LazyColumn {
            items(displayedBooks.size) { i ->
                BookCard(book = displayedBooks[i], onDelete = { viewModel.deleteBook(it) }, onProgressUpdate = { id, p -> viewModel.updateProgress(id, p) })
            }
        }
    }
}
