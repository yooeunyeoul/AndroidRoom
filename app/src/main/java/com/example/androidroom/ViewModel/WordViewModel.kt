package com.example.androidroom.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.androidroom.Repository.WordRepository
import com.example.androidroom.model.Word
import com.example.androidroom.model.WordRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *  UI 에 data 를 제공하는 역할을 합니다.
 *  Repository 와 Ui 사이에서 활동합니다.
 *  fragments 들 사이에서 data 를 공유하기위해 사용합니다.
 *  ViewModel 은 lifecycle library 의 일부분 입니다.
 *  activity 가 회전 되었을 때도, ViewModel 을 통해 데이터를 유지시킬 수 있습니다.
 *  참초 https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#8
 *
 *
 *  viewModel 은 LifeCycle 내에서 앱의 UI 데이터를 가지고 있습니다.
 *  액티비티와 프래그먼트 클래스로부터 데이터를 분리함으로써 당신의 앱이 single responsibility principle 을 더 보장할수 있다.
 *  액티비티와 프래그먼트들은 UI 그리는것을 보장받을 수 있고, 그러는 동안 뷰모델은 모든 UI에 필요한 데이터를 가지고 있거나, 처리합니다.
 *  ViewModel 에서는 UI에 사용할 변동하는 데이터를 감지하기위해 LiveData 를 사용 합니다.
 *  << ViewModel 를 사용하므로써 이점 >>
 *      1. data 를 관찰하는 observer 를 설치할수있고 , 데이터가 실제로 변화했을 때에만 UI를 업데이트 할 수 있다.
 *      2. ViewModel 을 사용함으로써 , Repository 와 UI 가 완변히 분리 될 수 있습니다.
 *      3. ViewModel 에서는 database 호출이 없으므로, 코드가 더 테스트에 용이하도록 만들수 있습니다.
 *
 *  안드로이드 X Lifecycler-ViewModel-ktx 라이브러리는 ViewModel 의 확장함수인 로서 ViewModelScope 를 추가하였습니다. 이것은 scope 관리를 더 쉽게 해줍니다.
 *
 */
class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    private val allWords: LiveData<List<Word>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application , viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    /**
     * Repository 의 insert 메소드를 호출하는 Wrapper insert  클래스
     * 이렇게하면 implementation insert() 메소드는 UI 로부터 완벽히 hidden 처리가 됩니다.
     * 우리가 원하는것은 main thread 에서 insert() 메소드를 호출하기를 원합니다.
     * 그래서 우리는 이전에만든 코루틴 스코프를 기반으로하는 새로운 코루틴을 만듭니다.
     * 왜냐하면 우리는 database 수행을 하기 때문입니다. 그래서 우리는 IO Dispatcher 를 사용합니다.
     * IO Dispatcher 는 안드로이드에서는 background Thread 이고   , Main 은 Main Thread 이다.
     */
    fun insert(word:Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

}