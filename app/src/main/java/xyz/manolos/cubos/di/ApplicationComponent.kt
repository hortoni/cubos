package xyz.manolos.cubos.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import xyz.manolos.cubos.movie.MovieComponent
import xyz.manolos.cubos.movie.MovieModule
import javax.inject.Singleton

@Component(modules = [ServiceModule::class])
@Singleton
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Context): Builder
        fun build(): ApplicationComponent

    }

    fun plusMovie(movieModule: MovieModule): MovieComponent

}