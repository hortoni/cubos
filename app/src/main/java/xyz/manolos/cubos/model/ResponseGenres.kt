package xyz.manolos.cubos.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseGenres (
    val genres: List<Genre>

)
