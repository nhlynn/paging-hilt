package com.nhlynn.paging_hilt.delegate

import com.nhlynn.paging_hilt.model.MovieVO

interface MovieDelegate {
    fun onViewMovie(movie: MovieVO)
}