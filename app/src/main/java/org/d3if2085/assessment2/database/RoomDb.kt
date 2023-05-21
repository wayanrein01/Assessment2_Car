package org.d3if2085.assessment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if2085.assessment2.database.dao.CarDao
import org.d3if2085.assessment2.database.dao.UserDao
import org.d3if2085.assessment2.database.entity.Car
import org.d3if2085.assessment2.database.entity.User

@Database(
    entities = [
        User::class,
        Car::class
    ],
    version = 2,
    exportSchema = false
)
abstract class RoomDb : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val carDao: CarDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getInstance(context: Context): RoomDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDb::class.java,
                        "tes.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}