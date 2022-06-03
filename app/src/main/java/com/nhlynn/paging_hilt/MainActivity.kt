package com.nhlynn.paging_hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nhlynn.paging_hilt.adapter.LoaderStateAdapter
import com.nhlynn.paging_hilt.adapter.MoviePagingAdapter
import com.nhlynn.paging_hilt.databinding.ActivityMainBinding
import com.nhlynn.paging_hilt.delegate.MovieDelegate
import com.nhlynn.paging_hilt.model.MovieVO
import com.nhlynn.paging_hilt.view_model.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MovieDelegate {
    private lateinit var binding: ActivityMainBinding

    private val mMovieViewModel by viewModels<MovieViewModel>()

    private lateinit var moviePagingAdapter: MoviePagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviePagingAdapter = MoviePagingAdapter(this)
        binding.rvMovie.setHasFixedSize(true)
        binding.rvMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMovie.adapter = moviePagingAdapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter { moviePagingAdapter.retry() },
            footer = LoaderStateAdapter { moviePagingAdapter.retry() }
        )

        lifecycleScope.launch {
            mMovieViewModel.result.observe(this@MainActivity) {
                binding.apply {
                    progressBar.visibility = View.GONE
                    rvMovie.visibility = View.VISIBLE
                }
                moviePagingAdapter.submitData(lifecycle, it)
            }
        }

        moviePagingAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    moviePagingAdapter.itemCount < 1
                ) {
                    rvMovie.isVisible = false
                    tvEmpty.isVisible = true
                } else {
                    tvEmpty.isVisible = false
                }
            }
        }

        binding.btnRetry.setOnClickListener {
            moviePagingAdapter.retry()
        }
    }

    override fun onViewMovie(movie: MovieVO) {
        Toast.makeText(this, movie.name, Toast.LENGTH_LONG).show()
    }
}