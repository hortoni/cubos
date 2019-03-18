package xyz.manolos.cubos.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.manolos.cubos.model.ResponseMovies


interface MovieService {

    @GET("discover/movie")
    fun fetchMoviesByGenreId(@Query("page") page: Int, @Query("with_genres") genreId: Long): Single<ResponseMovies>

    @GET("search/movie")
    fun fetchMoviesByQuery(@Query("page") page: Int, @Query("query") query: String): Single<ResponseMovies>

}