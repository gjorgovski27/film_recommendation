package com.fri.emp_projekt.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: Comment)

    @Query("SELECT * FROM comments WHERE mov_id = :movieId")
    suspend fun getCommentsByMovie(movieId: Int): List<Comment>

    @Query("SELECT * FROM comments WHERE user_id = :userId")
    suspend fun getCommentsByUser(userId: Int): List<Comment>

    @Query("DELETE FROM comments")
    suspend fun truncateComments()

    @Query("""
        SELECT comments.comment_id, comments.content, profiles.p_fullname AS username 
        FROM comments 
        INNER JOIN profiles ON comments.user_id = profiles.p_id 
        WHERE comments.mov_id = :movieId
    """)
    suspend fun getCommentsForMovie(movieId: Int): List<CommentWithUser>
}

data class CommentWithUser(
    val comment_id: Int,
    val content: String,
    val username: String
)
