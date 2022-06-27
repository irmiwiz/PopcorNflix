package com.irmiwiz.popcornflix.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.irmiwiz.popcornflix.core.helper.QueryType
import com.irmiwiz.popcornflix.domain.model.MovieResult
import com.irmiwiz.popcornflix.domain.repository.MovieRepository
import com.irmiwiz.popcornflix.getMovie
import com.irmiwiz.popcornflix.getMovieResponse
import io.mockk.MockKAnnotations
import io.mockk.MockKCancellation
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityViewModelTest {
    @RelaxedMockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: MainActivityViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = MainActivityViewModel(movieRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when getMovies get a lisf of movies from repository then MainActivityUiState is ShowMovies`() = runTest {
        coEvery { movieRepository.getListOfMovies("",1) } returns MovieResult.Success(getMovieResponse())
        viewModel.getMovies("")
        assert(viewModel.uiState.value is MainActivityUiState.ShowMovies)
    }

    @Test
    fun `when getMovies get a error from repository then MainActivityUiState is Error`() = runTest {
        coEvery { movieRepository.getListOfMovies("",1) } returns MovieResult.Error("fake error")
        viewModel.getMovies("")
        assert(viewModel.uiState.value is MainActivityUiState.Error)
    }

    @Test
    fun `when getMoreMovies get a lisf of movies from repository then MainActivityUiState is UpdateMovies`() = runTest {
        coEvery { movieRepository.getListOfMovies(QueryType.MOST_POPULAR.value,1) } returns MovieResult.Success(getMovieResponse())
        viewModel.getMoreMovies()
        assert(viewModel.uiState.value is MainActivityUiState.UpdateMovies)
    }

    @Test
    fun `when getMoreMovies get a error from repository then MainActivityUiState is Error`() = runTest {
        coEvery { movieRepository.getListOfMovies(QueryType.MOST_POPULAR.value,1) } returns MovieResult.Error("fake error")
        viewModel.getMoreMovies()
        assert(viewModel.uiState.value is MainActivityUiState.Error)
    }

    @Test
    fun `when searchMovies get a lisf of movies from repository then MainActivityUiState is ShowSearchList`() = runTest {
        coEvery { movieRepository.searchMovies("") } returns MovieResult.Success(getMovieResponse())
        viewModel.searchMovies("")
        assert(viewModel.uiState.value is MainActivityUiState.ShowSearchList)
    }

    @Test
    fun `when searchMovies get a error from repository then MainActivityUiState is Error`() = runTest {
        coEvery { movieRepository.searchMovies("") } returns MovieResult.Error("fake error")
        viewModel.searchMovies("")
        assert(viewModel.uiState.value is MainActivityUiState.Error)
    }


}