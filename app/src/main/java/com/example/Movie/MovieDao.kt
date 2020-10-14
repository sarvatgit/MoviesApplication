package com.example.Movie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {
    @Query("select * from MoviesDb")
    fun getVideos(): LiveData<List<MoviesDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( movie: List<MoviesDb>)
}
@Database(entities = [MoviesDb::class], version = 1)
abstract class MovDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
}

private lateinit var INSTANCE: MovDatabase

fun getDatabase(context: Context): MovDatabase {
    synchronized(MovDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MovDatabase::class.java,
                "movieslist").build()
        }
    }
    return INSTANCE
}