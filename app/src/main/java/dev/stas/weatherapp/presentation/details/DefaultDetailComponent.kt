package dev.stas.weatherapp.presentation.details

import com.arkivanov.decompose.ComponentContext

class DefaultDetailComponent(
    componentContext: ComponentContext
) : DetailComponent, ComponentContext by componentContext{

}