package com.fri.emp_projekt.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "comments",
    foreignKeys = [
        ForeignKey(
            entity = Profile::class,
            parentColumns = ["p_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["mov_id"],
            childColumns = ["mov_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Comment(
    @PrimaryKey(autoGenerate = true) val comment_id: Int = 0,
    val content: String,
    val user_id: Int,  // Foreign key linking to Profile
    val mov_id: Int    // Foreign key linking to Movie
)
