package xyz.manolos.inmovies.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.manolos.cubos.model.ResponseGenres
import xyz.manolos.cubos.model.ResponseMovies


interface MovieService {

    @GET("genre/movie/list")
    fun fetchGenres(): Single<ResponseGenres>

    @GET("discover/movie")
    fun fetchMoviesByGenreId(@Query("page") page: Int, @Query("with_genres") genreId: Int): Single<ResponseMovies>
}