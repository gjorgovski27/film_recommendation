package com.fri.emp_projekt.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns

@Dao
@RewriteQueriesToDropUnusedColumns
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movies WHERE mov_avg_rating > 4.0")
    suspend fun getRecommendedMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movies WHERE user_id = :userId")
    suspend fun getMoviesForUser(userId: Int): List<Movie>

    @Query("SELECT * FROM movies WHERE user_id = :userId AND mov_avg_rating > 4.0")
    suspend fun getRecommendedMoviesForUser(userId: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies WHERE mov_id = :movieId")
    suspend fun getMovieById(movieId: Int): Movie?

    @Query("DELETE FROM movies")
    suspend fun truncateMovies()
}
