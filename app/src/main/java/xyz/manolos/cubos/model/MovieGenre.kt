package xyz.manolos.cubos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "movie_genre",
    indices = [Index(value = ["id_movie", "id_genre"], unique = true)])
data class MovieGenre(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Long? = null,

    val id_movie: Long,
    val id_genre: Long
)

