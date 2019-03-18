package xyz.manolos.cubos

import android.app.Activity
import android.app.Application
import xyz.manolos.cubos.di.DaggerApplicationComponent

class CubosApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationContext(applicationContext)
            .build()
    }
}

val Activity.injector get() = (application as CubosApplication).component