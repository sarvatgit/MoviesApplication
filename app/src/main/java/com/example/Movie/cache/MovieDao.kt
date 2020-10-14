package com.example.Movie.cache

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.Movie.MovieModel
import com.example.Movie.data.Movies

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

class MoviesDbHelper(context: Context?) : SQLiteOpenHelper(context, "MoviesDB.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        db.execSQL(SQL_CREATE_ENTRIES1)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
       db.execSQL(SQL_DELETE_ENTRIES1)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: MovieModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put("id", user.id)
        values.put("title", user.title)
        values.put("posterPath", user.posterPath)
        values.put("overview", user.overview)
        values.put("rating", user.rating)
        values.put("backdropPath", user.backdropPath)
        values.put("releasedate", user.releaseDate)
        values.put("adult", user.adult)
        values.put("runtime", user.runtime)
        values.put("origlang", user.origlang)
        values.put("budget", user.budget)
        values.put("genres", user.genres)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert("MoviesTable", null, values)

        return true
    }
    @Throws(SQLiteConstraintException::class)
    fun insertMovie1(user: MovieModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put("id", user.id)
        values.put("title", user.title)
        values.put("posterPath", user.posterPath)
        values.put("overview", user.overview)
        values.put("rating", user.rating)
        values.put("backdropPath", user.backdropPath)
        values.put("releasedate", user.releaseDate)
        values.put("adult", user.adult)
        values.put("runtime", user.runtime)
        values.put("origlang", user.origlang)
        values.put("budget", user.budget)
        values.put("genres", user.genres)


        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert("MoviesTable1", null, values)

        return true
    }



    fun readAllMovies(): ArrayList<Movies> {
        val users = ArrayList<Movies>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + "MoviesTable", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: String
        var title: String
        var posterPath: String
        var overview: String
        var rating: String
        var backdropPath: String

        var releaseDate: String
        var adult: String
        var runtime: String
        var origlang: String
        var budget: String
        var genres: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getString(cursor.getColumnIndex("id"))
                title = cursor.getString(cursor.getColumnIndex("title"))
                posterPath = cursor.getString(cursor.getColumnIndex("posterPath"))
                overview = cursor.getString(cursor.getColumnIndex("overview"))
                rating = cursor.getString(cursor.getColumnIndex("rating"))
                backdropPath = cursor.getString(cursor.getColumnIndex("backdropPath"))

                releaseDate = cursor.getString(cursor.getColumnIndex("releaseDate"))
                adult = cursor.getString(cursor.getColumnIndex("adult"))
                runtime = cursor.getString(cursor.getColumnIndex("runtime"))
                origlang = cursor.getString(cursor.getColumnIndex("origlang"))

                budget = cursor.getString(cursor.getColumnIndex("budget"))
                genres = cursor.getString(cursor.getColumnIndex("genres"))



                users.add(
                    Movies(
                        id.toLong(),
                        title,
                        overview,
                        posterPath,
                        backdropPath,
                        rating.toFloat(),
                        releaseDate,
                        adult,
                        runtime.toLong(),
                        origlang,
                        budget,
                        genres
                    )
                )
                cursor.moveToNext()
            }
        }
        return users
    }
    fun readAllMovies11(): ArrayList<Movies> {
        val users = ArrayList<Movies>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + "MoviesTable1", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES1)
            return ArrayList()
        }

        var id: String
        var title: String
        var posterPath: String
        var overview: String
        var rating: String
        var backdropPath: String

        var releaseDate: String
        var adult: String
        var runtime: String
        var origlang: String
        var budget: String
        var genres: String


        //var budget: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getString(cursor.getColumnIndex("id"))
                title = cursor.getString(cursor.getColumnIndex("title"))
                posterPath = cursor.getString(cursor.getColumnIndex("posterPath"))
                overview = cursor.getString(cursor.getColumnIndex("overview"))
                rating = cursor.getString(cursor.getColumnIndex("rating"))
                backdropPath = cursor.getString(cursor.getColumnIndex("backdropPath"))

                releaseDate = cursor.getString(cursor.getColumnIndex("releaseDate"))
                adult = cursor.getString(cursor.getColumnIndex("adult"))
                runtime = cursor.getString(cursor.getColumnIndex("runtime"))
                origlang = cursor.getString(cursor.getColumnIndex("origlang"))

                budget = cursor.getString(cursor.getColumnIndex("budget"))
                genres = cursor.getString(cursor.getColumnIndex("genres"))



                users.add(
                    Movies(
                        id.toLong(),
                        title,
                        overview,
                        posterPath,
                        backdropPath,
                        rating.toFloat(),
                        releaseDate,
                        adult,
                        runtime.toLong(),
                        origlang,
                        budget,
                        genres
                    )
                )
                cursor.moveToNext()
            }
        }
        return users
    }


    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "MoviesDB.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + "MoviesTable" + " (" +
                    "id" + " TEXT PRIMARY KEY," +
                    "title" + " TEXT," +
                    "overview"+ " TEXT," +
                    "posterPath" + " TEXT," +
                    "backdropPath"+ " TEXT," +
                    "rating"+ " TEXT," +
                    "releaseDate" + " TEXT," +
                    "adult"+ " TEXT," +
                    "runtime"+ " TEXT," +
                    "origlang" + " TEXT," +
                    "budget"+ " TEXT," +
                    "genres"+ " TEXT)"

        private val SQL_CREATE_ENTRIES1 =
            "CREATE TABLE " + "MoviesTable1" + " (" +
                    "id" + " TEXT PRIMARY KEY," +
                    "title" + " TEXT," +
                    "overview"+ " TEXT," +
                    "posterPath" + " TEXT," +
                    "backdropPath"+ " TEXT," +
                    "rating"+ " TEXT," +
                    "releaseDate" + " TEXT," +
                    "adult"+ " TEXT," +
                    "runtime"+ " TEXT," +
                    "origlang" + " TEXT," +
                    "budget"+ " TEXT," +
                    "genres"+ " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + "MoviesTable"
        private val SQL_DELETE_ENTRIES1 = "DROP TABLE IF EXISTS " + "MoviesTable1"
    }


}