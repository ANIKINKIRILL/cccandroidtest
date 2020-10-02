package com.anikinkirill.cccandroidtest.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
data class Person(
    @PrimaryKey
    @NonNull
    val id: String = "",
    val first_name: String? = null,
    val last_name: String? = null,
    val email: String? = null,
    val phone_number: String? = null
)