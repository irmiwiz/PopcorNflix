package com.irmiwiz.popcornflix

import com.irmiwiz.popcornflix.data.network.MovieClient
import com.irmiwiz.popcornflix.domain.model.Movie
import com.irmiwiz.popcornflix.domain.model.MovieResult
import com.irmiwiz.popcornflix.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MovieRepositoryTest  {

    @MockK
    private lateinit var moviesClient: MovieClient

    lateinit var movieRepository: MovieRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        movieRepository = MovieRepository(moviesClient)
    }

    @Test
    fun `when getMovieDetails return an error then get a MovieResult Error`() = runBlocking {
        //given
        coEvery { moviesClient.getMovieDetails(1) } returns MovieResult.Error("this is a fake error")

        //when
        val resutl = movieRepository.getMovieDetails(1)
        //test
        coVerify(exactly = 1) { moviesClient.getMovieDetails(1) }
        assert(resutl is MovieResult.Error)

    }

    @Test
    fun `when getMovieDetails is success then get a MovieResult success `() =
        runBlocking {
            //given
            coEvery { moviesClient.getMovieDetails(1) } returns MovieResult.Success(getMovie())

            //when
            val resutl = movieRepository.getMovieDetails(1)

            //test
            coVerify(exactly = 1) { moviesClient.getMovieDetails(1) }
            assert(resutl is MovieResult.Success)
        }

    @Test
    fun `when getListOfMovies return an error then get a MovieResult Error`() = runBlocking {
        //given
        coEvery {
            moviesClient.getListOfMovies(
                "",
                1
            )
        } returns MovieResult.Error("this is a fake error")

        //when
        val resutl = movieRepository.getListOfMovies("", 1)

        //test
        coVerify(exactly = 1) { moviesClient.getListOfMovies("", 1) }
        assert(resutl is MovieResult.Error)

    }

    @Test
    fun `when getListOfMovies is success then get a MovieResult success`() =
        runBlocking {
            //given
            coEvery { moviesClient.getListOfMovies("", 1) } returns MovieResult.Success(getMovieResponse())

            //when
            val resutl = movieRepository.getListOfMovies("", 1)

            //test
            coVerify(exactly = 1) { moviesClient.getListOfMovies("", 1) }
            assert(resutl is MovieResult.Success)
        }

    @Test
    fun `when searchMovies returns an error then get a MovieResult Error`() = runBlocking {
        //given
        coEvery { moviesClient.searchMovie("") } returns MovieResult.Error("this is a fake error")

        //when
        val resutl = movieRepository.searchMovies("")
        //test
        coVerify(exactly = 1) { moviesClient.searchMovie("") }
        assert(resutl is MovieResult.Error)

    }

    @Test
    fun `when searchMovies is success then get a MovieResult success`() =
        runBlocking {
            //given
            coEvery { moviesClient.searchMovie("") } returns MovieResult.Success(getMovieResponse())

            //when
            val resutl = movieRepository.searchMovies("")

            //test
            coVerify(exactly = 1) { moviesClient.searchMovie("") }
            assert(resutl is MovieResult.Success)
        }

    @Test
    fun `when getCast return an error then get a MovieResult Error`() = runBlocking {
        //given
        coEvery { moviesClient.getCast(1) } returns MovieResult.Error("this is a fake error")

        //when
        val resutl = movieRepository.getCast(1)
        //test
        coVerify(exactly = 1) { moviesClient.getCast(1) }
        assert(resutl is MovieResult.Error)

    }

    @Test
    fun `when getCast is success then get a MovieResult success`() =
        runBlocking {
            //given
            coEvery { moviesClient.getCast(1) } returns MovieResult.Success(listOf(getCast(), getCast()))

            //when
            val resutl = movieRepository.getCast(1)

            //test
            coVerify(exactly = 1) { moviesClient.getCast(1) }
            assert(resutl is MovieResult.Success)
        }
}