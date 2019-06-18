package com.example.androidroom.Repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.androidroom.model.Word
import com.example.androidroom.model.WordDao

/**
 * Repository 클래스는 data에 접근하는 클래스 입니다.
 * Repository는 쿼리를 관리하고 백엔드 처리를 하게 해줍니다.
 * Repository는 네트워크로 부터 온 데이타를 어디에 패치할것인지, 로컬 데이터베이스에서 결과 캐시를 어디에 사용할것인지 에 관한 로직 클래스를 implements 합니다.
 *
 */
class WordRepository(private val wordDao: WordDao) {
    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    /**
     * WorkerThread 를 표기함으로써 UI 쓰레드로 실행되지 않아야할것을 명시합니다.
     * Suspend 는 컴파일러에게 코루틴 혹은 다른 suspending function 에서 불려져야 한다는 것을 알려줍니다.
     *
     */
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }


}