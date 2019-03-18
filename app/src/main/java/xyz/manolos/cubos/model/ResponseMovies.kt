package xyz.manolos.cubos.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseMovies (
    val results: List<Movie>,
    val page: Int,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results")  val totalResults: Int
)
