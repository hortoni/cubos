package xyz.manolos.cubos.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
@Entity(tableName = "movie")
data class Movie @JvmOverloads constructor(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    val overview: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "genre_ids") @Ignore var genreIds: List<Long> = emptyList(),
    val genreId: Int?
) : Parcelable

