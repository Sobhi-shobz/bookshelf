package com.example.bookshelf.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookshelf.data.Book

@Composable
fun BookCard(book: Book, onDelete: (Int) -> Unit, onProgressUpdate: (Int, Int) -> Unit) {
    Card(Modifier.padding(8.dp).fillMaxWidth(), elevation = CardDefaults.cardElevation(6.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Text("Author: ${book.author}")
            Text("Genre: ${book.genre}")
            Text("Progress: ${book.progress}%")

            Slider(
                value = book.progress.toFloat(),
                onValueChange = { onProgressUpdate(book.id, it.toInt()) },
                valueRange = 0f..100f,
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = { onDelete(book.id) }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)) {
                Text("Delete")
            }
        }
    }
}
