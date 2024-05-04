package dev.stas.weatherapp

import android.app.Application
import dev.stas.weatherapp.di.ApplicationComponent
import dev.stas.weatherapp.di.DaggerApplicationComponent

class WeatherApp: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}