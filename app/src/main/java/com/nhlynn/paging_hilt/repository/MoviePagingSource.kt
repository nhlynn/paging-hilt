package com.nhlynn.paging_hilt.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nhlynn.paging_hilt.model.MovieVO
import com.nhlynn.paging_hilt.network.ApiService
import javax.inject.Inject

class MoviePagingSource
@Inject constructor(private val apiService: ApiService) : PagingSource<Int, MovieVO>() {
    override fun getRefreshKey(state: PagingState<Int, MovieVO>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieVO> {
        val page = params.key ?: 1

        return try {

            val data = apiService.getCharacter(page)

            LoadResult.Page(
                data = data.body()?.character!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.body()?.character?.isEmpty()!!) null else page + 1
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}