package ru.example.memesapp.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.example.memesapp.data.models.OperationResult
import ru.example.memesapp.data.models.RawData
import ru.example.memesapp.presentation.models.ImageItem
import ru.example.memesapp.presentation.models.PageState
import ru.example.memesapp.presentation.repository.IRepository
import ru.example.memesapp.presentation.utils.toImageItem

class ImageViewModel(private val repository: IRepository<List<RawData>>, private val category: String) : ViewModel() {
    private var currentPage = 0

    private val _pageState = MutableStateFlow<PageState<List<ImageItem>>>(PageState.Empty)
    val pageState = _pageState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            _pageState.value = PageState.Loading
            val result = repository.loadData(currentPage++, category)
            delay(1000)
            when(result) {
                is OperationResult.Success -> {
                    if (result.data.isNotEmpty())
                        _pageState.value = PageState.Success(result.data.map { it.toImageItem() })
                    else {
                        currentPage--
                        _pageState.value = PageState.Error("List is empty")
                    }
                }
                is OperationResult.Error -> {
                    currentPage--
                    _pageState.value = PageState.Error(result.error.localizedMessage ?: "")
                }
            }
        }
    }
}