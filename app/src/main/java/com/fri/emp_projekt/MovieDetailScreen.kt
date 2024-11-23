package com.fri.emp_projekt

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fri.emp_projekt.data.Comment
import com.fri.emp_projekt.data.CommentDao
import com.fri.emp_projekt.data.CommentWithUser
import com.fri.emp_projekt.data.Movie
import com.fri.emp_projekt.data.MovieDao
import com.fri.emp_projekt.shared.RatingStars
import kotlinx.coroutines.launch

@Composable
fun MovieDetailScreen(
    movieDao: MovieDao,
    commentDao: CommentDao,
    movieId: Int,
    navController: NavHostController,
    userId: Int // Pass the logged-in user's ID
) {
    val movie = remember { mutableStateOf<Movie?>(null) }
    val comments = remember { mutableStateOf<List<CommentWithUser>>(emptyList()) }
    var newComment by remember { mutableStateOf("") } // To hold the content of the new comment
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val fetchedMovie = movieDao.getMovieById(movieId)  // Fetch movie by ID
        movie.value = fetchedMovie
        val fetchedComments = commentDao.getCommentsForMovie(movieId)  // Fetch comments for the movie
        comments.value = fetchedComments
    }

    movie.value?.let { selectedMovie ->
        LazyColumn( // Use LazyColumn for scrolling
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Movie Poster
                Image(
                    painter = painterResource(id = getPosterResource(selectedMovie.mov_poster)),
                    contentDescription = selectedMovie.mov_title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            item {
                // Movie Title
                Text(
                    text = selectedMovie.mov_title,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            item {
                // Rating with Stars
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingStars(rating = selectedMovie.mov_avg_rating.toFloat())
                    Text(
                        text = " (${selectedMovie.mov_avg_rating}/5)",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            item {
                // Movie Description
                Text(
                    text = selectedMovie.mov_description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                // Movie Director
                Text(
                    text = "Director: ${selectedMovie.mov_director}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                // Comments Section Header
                Text(
                    text = "Comments:",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            items(comments.value) { comment ->
                // Individual Comment
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = comment.username,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = comment.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            item {
                // Add Comment Section Header
                Text(
                    text = "Add a comment:",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            item {
                // Comment Input Box
                OutlinedTextField(
                    value = newComment,
                    onValueChange = { newComment = it },
                    label = { Text("Your comment...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                )
            }

            item {
                // Post Button
                Button(
                    onClick = {
                        if (newComment.isNotBlank()) {
                            coroutineScope.launch {
                                val comment = Comment(
                                    content = newComment,
                                    user_id = userId,
                                    mov_id = movieId
                                )
                                commentDao.insertComment(comment) // Save the comment in the database
                                newComment = "" // Clear the textbox
                                val updatedComments = commentDao.getCommentsForMovie(movieId)
                                comments.value = updatedComments // Refresh the comments
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 4.dp) // Add vertical padding
                ) {
                    Text(
                        text = "Post",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp) // Inner padding
                    )
                }
            }

        }
    } ?: run {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}

@Composable
private fun getPosterResource(posterName: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(
        posterName, "drawable", context.packageName
    )
}
