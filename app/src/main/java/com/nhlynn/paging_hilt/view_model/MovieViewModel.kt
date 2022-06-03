package com.nhlynn.paging_hilt.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nhlynn.paging_hilt.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(repository: MovieRepository) : ViewModel() {
//    fun getListData(): Flow<PagingData<MovieVO>> {
//        return Pager(config = PagingConfig(
//            pageSize = 20,
//            maxSize = 200,
//            enablePlaceholders = false
//        ),
//            pagingSourceFactory = { repository }).flow.cachedIn(viewModelScope)
//    }

    val result = repository.getResult().cachedIn(viewModelScope)
}