package br.com.knowledge.data.database.dao

import androidx.room.*
import br.com.knowledge.data.model.ActiveUser
import kotlinx.coroutines.flow.Flow

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
}