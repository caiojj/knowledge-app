package br.com.knowledge.data.database.dao

import androidx.room.*
import br.com.knowledge.data.model.ActiveUser
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull

@Dao
interface ActiveUserDao {

    @Query("SELECT token FROM active_user" )
    fun getToken() : Flow<List<String>>

    @Query("SELECT * FROM active_user")
    fun findAll(): Flow<List<ActiveUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(activeUser: ActiveUser)

    @Query("DELETE FROM active_user WHERE email = :email")
    fun delete(email: String)

    @Query("SELECT email FROM active_user")
    fun getEmail() : Flow<List<String>>

    @Query("UPDATE active_user set imageUrl = :url WHERE id = :id")
    fun updateUrl(url: String, id: Long)
}