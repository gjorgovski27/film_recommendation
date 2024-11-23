package com.fri.emp_projekt

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fri.emp_projekt.data.Movie
import com.fri.emp_projekt.data.MovieDao
import com.fri.emp_projekt.shared.RatingStars
import com.fri.emp_projekt.viewmodel.UserViewModel



@Composable
fun HomeScreen(
    movieDao: MovieDao,
    userName: String,
    navController: NavHostController,
    userViewModel: UserViewModel // Added UserViewModel as a parameter
) {
    val movieList = remember { mutableStateListOf<Movie>() }
    val recommendedMovies = remember { mutableStateListOf<Movie>() }

    LaunchedEffect(Unit) {
        val movies = movieDao.getAllMovies() // Fetch movies from the database
        movieList.clear()
        movieList.addAll(movies)

        // Filter movies with rating >= 4 for the recommended section
        recommendedMovies.clear()
        recommendedMovies.addAll(movies.filter { it.mov_avg_rating >= 4 })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Welcome Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = "Profile Icon",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
            Column {
                Text(
                    text = "Welcome back",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = userName,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        // Search Section
        OutlinedTextField(
            value = "",
            onValueChange = { /* Implement search logic */ },
            placeholder = { Text(text = "Search") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(24.dp)
                )
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // My List Section
        SectionHeader(title = "My List", onSeeAllClick = { /* Navigate to My List */ })

        LazyRow(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
        ) {
            items(movieList) { movie ->
                MovieCard(movie = movie, onClick = {
                    navController.navigate("movieDetail/${movie.mov_id}")
                })
            }
        }

        // Recommended Section
        SectionHeader(title = "Recommended", onSeeAllClick = { /* Navigate to Recommendations */ })

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(recommendedMovies) { movie ->
                MovieCard(movie = movie, onClick = {
                    navController.navigate("movieDetail/${movie.mov_id}")
                })
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(120.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = getPosterResource(movie.mov_poster)
                ),
                contentDescription = movie.mov_title,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Text(
                text = movie.mov_title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
            RatingStars(rating = movie.mov_avg_rating.toFloat())
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

@Composable
fun SectionHeader(title: String, onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextButton(onClick = onSeeAllClick) {
            Text(text = "See All", color = MaterialTheme.colorScheme.primary)
        }
    }
}

