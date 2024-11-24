# Film Recommendation App

## Overview

The Film Recommendation App is an Android application designed to showcase a curated collection of movies, with features like personalized recommendations, user authentication, and a comment system for movie reviews. Users can log in, browse movies, leave comments, and explore films based on their ratings.

This project is developed using **Jetpack Compose** for the UI and **Room Database** for local data storage. It demonstrates the integration of modern Android development practices.

## Features

**User Authentication**: Users can log in or sign up to access personalized features.

**Movie Recommendations**: Explore movies with a rating.

**Comment System**: View and post comments for each movie.

**Dynamic UI**: Jetpack Compose enables smooth and interactive UI components.

## Prerequisites

To build and run this app, ensure you have:

**Android Studio** (latest stable version recommended).

**JDK 24** or higher.

**Internet access** (for any dependencies).

### Installation:

- Clone the Repository

- Open in Android Studio

- Launch Android Studio.

- Select Open an existing project and navigate to the cloned repository.

### Build the Project:

Wait for Gradle to sync.
Ensure there are no build errors.

### Run the App:

Select a connected device or emulator.
Click the Run button in Android Studio.

### Demonstration Data

The MainActivity class includes code for inserting placeholder data into the local Room database to demonstrate functionality. This code is currently commented out.

### Placeholder Data


        // Populate the database with movies
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
### How to Enable Placeholder Data:

Uncomment the placeholder data code in MainActivity.
Rebuild and run the app.
The app will populate the database with sample movie data for demonstration purposes.
