package com.fri.emp_projekt.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class Profile(
    @PrimaryKey(autoGenerate = true) val p_id: Int = 0,
    val p_fullname: String,
    val p_phone_no: String,
    val p_password: String,
    val p_email: String
)
