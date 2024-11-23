package com.fri.emp_projekt.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns

@Dao
@RewriteQueriesToDropUnusedColumns
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)

    @Query("SELECT * FROM profiles WHERE p_email = :email AND p_password = :password")
    suspend fun getProfile(email: String, password: String): Profile?


    @Query("SELECT * FROM profiles WHERE p_id = :userId")
    suspend fun getProfileById(userId: Int): Profile?

    @Query("DELETE FROM profiles")
    suspend fun truncateProfiles()
}


