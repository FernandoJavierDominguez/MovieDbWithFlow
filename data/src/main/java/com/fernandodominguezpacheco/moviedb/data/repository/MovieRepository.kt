package com.fernandodominguezpacheco.moviedb.data.repository

import com.fernandodominguezpacheco.moviedb.data.datasource.LocalMovieDataSource
import com.fernandodominguezpacheco.moviedb.data.datasource.RemoteMovieDataSource
import com.fernandodominguezpacheco.moviedb.domain.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val localMovieDataSource: LocalMovieDataSource,
    private val remoteMovieDataSource: RemoteMovieDataSource
) {
    suspend fun addMovies(){
        if(localMovieDataSource.isEmpty()){
            localMovieDataSource.addMovies(remoteMovieDataSource.getAllMovies())
        }
    }
    fun getAllMovies() : Flow<List<Movie>> = localMovieDataSource.getAllMoviesWithGenresAndActors()

}