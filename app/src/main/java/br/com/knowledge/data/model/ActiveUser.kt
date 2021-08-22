package br.com.knowledge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "active_user")
data class ActiveUser(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val email: String,
    val admin: Boolean,
    val token: String,
)