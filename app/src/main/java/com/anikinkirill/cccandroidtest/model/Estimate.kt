package com.anikinkirill.cccandroidtest.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "estimate_table",
    foreignKeys = [
        ForeignKey(
            entity = Person::class,
            parentColumns = ["id"],
            childColumns = ["created_by"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Estimate(
    @PrimaryKey
    @NonNull
    val id: String = "",
    val company: String? = null,
    val address: String? = null,
    val number: Int? = null,
    val revision_number: Int? = null,
    val created_date: String? = null,
    val created_by: String? = null,
    val requested_by: String? = null,
    val contact: String? = null
)