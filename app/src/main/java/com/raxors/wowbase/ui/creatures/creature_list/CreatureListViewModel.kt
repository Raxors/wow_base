package com.raxors.wowbase.ui.creatures.creature_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.liveData
import androidx.paging.map
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.raxors.wowbase.core.base.BaseViewModel
import com.raxors.wowbase.data.api.WoWApi
import com.raxors.wowbase.domain.interactors.CreatureInteractor
import com.raxors.wowbase.domain.models.creature.CreatureSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class CreatureListViewModel @Inject constructor(
    private val interactor: CreatureInteractor,
    private val api: WoWApi
) : BaseViewModel<CreatureListScreenState>() {

    override val initialState = CreatureListScreenState()

    override fun handleError(error: Throwable) {
        _state.update { it.copy(error = Exception(error), isLoading = false) }
    }

    override fun setLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    // TODO перенести все выше описанные функции в базовый класс BaseViewModel

    init {
        fetchData()
    }

    private fun fetchData() {
        launch {
            interactor.searchCreatures().flow
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collectLatest { creatures ->
                    _state.update { it.copy(creatures = creatures) }
                    setLoading(false)
                }
        }
    }
}