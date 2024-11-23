package com.fri.emp_projekt.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) val mov_id: Int = 0,
    val mov_title: String,
    val mov_description: String,
    val mov_avg_rating: Double,  // This stores the rating
    val mov_director: String,
    val mov_poster: String,
    val user_id: Int // Relates the movie to a specific user
)

