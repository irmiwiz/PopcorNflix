package com.irmiwiz.popcornflix.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.irmiwiz.popcornflix.domain.model.MovieResult
import com.irmiwiz.popcornflix.domain.repository.MovieRepository
import com.irmiwiz.popcornflix.getCast
import com.irmiwiz.popcornflix.getMovie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailSharedViewModelTest{
    @MockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: MovieDetailSharedViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = MovieDetailSharedViewModel(movieRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when repository on getMovieDetail is success then DetailActivityUiState is showMovieDetail`() = runTest {
        coEvery { movieRepository.getMovieDetails(1) } returns MovieResult.Success(getMovie())
        viewModel.getMovieDetail(1)
        assert(viewModel.viewState.value is DetailActivityUiState.ShowMovieDetail)
    }

    @Test
    fun `when repository on getMovieDetail fails then DetailActivityUiState is ShowErrorMessage`() = runTest {
        coEvery { movieRepository.getMovieDetails(1) } returns MovieResult.Error("")
        viewModel.getMovieDetail(1)
        assert(viewModel.viewState.value is DetailActivityUiState.ShowErrorMessage)
    }
}