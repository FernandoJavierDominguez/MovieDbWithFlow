package com.fernandodominguezpacheco.moviedb.framework.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<Movie>)

    @Transaction @Query("SELECT * FROM Movie")
    fun getAllMoviesWithActorsAndGenre() : Flow<List<MovieWithActorsAndGenres>>

    @Transaction @Query("SELECT * FROM Movie WHERE movieId = :movieId")
    fun getMovieByIdWithGenresAndGenre(movieId: Int) : Flow<MovieWithActorsAndGenres>

    @Query("SELECT COUNT(movieId) FROM Movie")
    suspend fun movieCount() : Int

    @Transaction @Query("SELECT * FROM Movie WHERE title LIKE  '%' ||  :text  ||  '%'  ")
    fun getAllMoviesWithActorsAndGenreBySearch(text: String): Flow<List<MovieWithActorsAndGenres>>


}