package com.example.androidroom.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Dao 클래스는 쿼리를 처리하는 클래스 입니다
 * UI 퍼포먼스를 떨어뜨리지 않기위해, background Thread 에서 운용 되며 비동기적으로 쿼리의 결과값으로, LiveData 를 반환합니다.
 */
@Dao
interface WordDao {

    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAllWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    fun deleteAll()
}