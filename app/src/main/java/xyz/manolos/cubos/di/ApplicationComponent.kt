package xyz.manolos.cubos.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import xyz.manolos.cubos.movie.MovieComponent
import xyz.manolos.cubos.movie.MovieModule
import xyz.manolos.cubos.search.SearchResultsComponent
import xyz.manolos.cubos.search.SearchResultsModule
import javax.inject.Singleton

@Component(modules = [ServiceModule::class, RoomModule::class])
@Singleton
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Context): Builder
        fun build(): ApplicationComponent

    }

    fun plusMovie(movieModule: MovieModule): MovieComponent
    fun plusSearchResults(searchResultsModule: SearchResultsModule): SearchResultsComponent

}