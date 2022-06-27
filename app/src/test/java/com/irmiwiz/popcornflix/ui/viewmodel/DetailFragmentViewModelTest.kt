package com.irmiwiz.popcornflix.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.irmiwiz.popcornflix.domain.model.MovieResult
import com.irmiwiz.popcornflix.domain.repository.MovieRepository
import com.irmiwiz.popcornflix.getCast
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailFragmentViewModelTest {
    @RelaxedMockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: DetailFragmentViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = DetailFragmentViewModel(movieRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when repository getCast is success DetailFragmentUiState is ShowCast`() = runTest {
        val result = MovieResult.Success(listOf(getCast()))
        coEvery { movieRepository.getCast(1) } returns result
        viewModel.getCast(1)
        assert(viewModel.uiState.value is DetailFragmentUiState.ShowCast)
    }

    @Test
    fun `when repository getCast fails DetailFragmentUiState is ShowErrorMessage`() = runTest {
        val result = MovieResult.Error("this is a generic error")
        coEvery { movieRepository.getCast(1) } returns result
        viewModel.getCast(1)
        assert(viewModel.uiState.value is DetailFragmentUiState.ShowErrorMessage)
    }
}