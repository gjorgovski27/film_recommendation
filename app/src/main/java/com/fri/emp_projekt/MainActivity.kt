package com.fri.emp_projekt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.facebook.stetho.Stetho
import com.fri.emp_projekt.data.AppDatabase
import com.fri.emp_projekt.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels() // Correctly initialize UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Stetho
        Stetho.initializeWithDefaults(this)

        val database = AppDatabase.getDatabase(applicationContext)
        val movieDao = database.movieDao()
        val profileDao = database.profileDao()
        val commentDao = database.commentDao()

/*
        // Populate the database with 10 movies
        lifecycleScope.launch(Dispatchers.IO) {

            // Clear the tables
            movieDao.truncateMovies()
            profileDao.truncateProfiles()

            val movies = listOf(
                Movie(100,"Inception", "Cobb steals information from his targets by entering their dreams. He is wanted for his alleged role in his wife's murder and his only chance at redemption is to perform a nearly impossible task.", 4.8, "Christopher Nolan", "inception", 1),
                Movie(101, "The Matrix", "Neo, a computer programmer and hacker, has always questioned the reality of the world around him. His suspicions are confirmed when Morpheus, a rebel leader, contacts him and reveals the truth to him.", 4.7, "Lana Wachowski, Lilly Wachowski", "matrix", 1),
                Movie(102, "The Dark Knight", "Batman has a new foe, the Joker, who is an accomplished criminal hell-bent on decimating Gotham City. Together with Gordon and Harvey Dent, Batman struggles to thwart the Joker before it is too late.", 4.9, "Christopher Nolan", "dark_knight", 1),
                Movie(103, "Interstellar", "When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, along with a team of researchers, to find a new planet for humans.", 4.8, "Christopher Nolan", "interstellar", 1),
                Movie(104, "Parasite", "The struggling Kim family sees an opportunity when the son starts working for the wealthy Park family. Soon, all of them find a way to work within the same household and start living a parasitic life.", 2.2, "Bong Joon Ho", "parasite", 1),
                Movie(105, "Avengers: Endgame", "After Thanos, an intergalactic warlord, disintegrates half of the universe, the Avengers must reunite and assemble again to reinvigorate their trounced allies and restore balance.", 3.8, "Anthony Russo, Joe Russo", "avengers", 1),
                Movie(106, "Pulp Fiction", "In the realm of underworld, a series of incidents intertwines the lives of two Los Angeles mobsters, a gangster's wife, a boxer and two small-time criminals.", 4.5, "Quentin Tarantino", "pulp_fiction", 1),
                Movie(107, "Fight Club", "Unhappy with his capitalistic lifestyle, a white-collared insomniac forms an underground fight club with Tyler, a careless soap salesman. Soon, their venture spirals down into something sinister.", 4.6, "David Fincher", "fight_club", 1),
                Movie(108, "The Godfather", "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son, Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.", 4.9, "Francis Ford Coppola", "godfather", 1),
                Movie(109, "The Shawshank Redemption", "Andy Dufresne, a successful banker, is arrested for the murders of his wife and her lover, and is sentenced to life imprisonment at the Shawshank prison. He becomes the most unconventional prisoner.", 4.9, "Frank Darabont", "shawshank", 1),
                Movie(110, "The Green Mile", "Paul Edgecomb, the head guard of a prison, meets an inmate, John Coffey, a black man who is accused of murdering two girls. His life changes drastically when he discovers that John has a special gift.", 4.9, "Frank Darabont", "green_mile", 1)
            )

            movieDao.insertMovies(movies)

            val profileDao = database.profileDao()


            lifecycleScope.launch(Dispatchers.IO) {
                val user = Profile(
                    p_id = 4,
                    p_fullname = "Sime Dazdev",
                    p_phone_no = "075500000",
                    p_password = "12345",
                    p_email = "sime@email.com"
                )
                profileDao.insertProfile(user)
            }
        }


 */

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(navController, profileDao, userViewModel)
                }
                composable("home/{userName}") { backStackEntry ->
                    val userName = backStackEntry.arguments?.getString("userName") ?: "Guest"
                    HomeScreen(movieDao, userName, navController, userViewModel) // Pass UserViewModel
                }
                composable("movieDetail/{movieId}") { backStackEntry ->
                    val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 0
                    MovieDetailScreen(
                        movieDao = movieDao,
                        commentDao = commentDao,
                        movieId = movieId,
                        navController = navController,
                        userId = userViewModel.userId.value ?: 0 // Retrieve userId from ViewModel
                    )
                }
                composable("signup") {
                    SignUpScreen(navController)
                }
            }
        }
    }
}
