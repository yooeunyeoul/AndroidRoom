package com.example.androidroom.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope


/**
 * Room 은 SQLList 데이터베이스의 상위 계층 입니다.
 * Room은 SQLiteOpenHelper 의 대부분의 기능을 수행합니다.
 * Room 은 Dao 를 이용하여 쿼리를 수행합니다.
 * 대개 Room Database instance 는 앱에 하나만 필요로 합니다.
 * WordRoomDatabase 는 멀티 instatnce 를 방지하기위해 singleTon 을 구현합니다.
 */
@Database(entities = [Word::class], version = 1)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope:CoroutineScope): WordRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "Word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
