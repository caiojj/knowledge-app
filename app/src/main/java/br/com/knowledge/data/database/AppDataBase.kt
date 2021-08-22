package br.com.knowledge.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.knowledge.data.database.dao.ActiveUserDao
import br.com.knowledge.data.module.ActiveUser

@Database(entities = [ActiveUser::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun activeUserDao(): ActiveUserDao

    companion object {
        fun getInstance(context: Context) : AppDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "knowledge",
            ).fallbackToDestructiveMigration().build()
        }
    }
}