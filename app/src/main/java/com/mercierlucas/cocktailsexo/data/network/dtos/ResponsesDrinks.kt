package com.mercierlucas.cocktailsexo.data.network.dtos

import com.mercierlucas.cocktailsexo.data.local.model.DrinkLiteEntity
import com.squareup.moshi.Json



data class ResponseDrinkDetailsDto(
    @Json(name = "drinks")
    val drinksDetailsDto: List<DrinkDetailsDto>
)

data class ResponseAllDrinksLite(
    @Json(name = "drinks")
    val drinksLiteDto: List<DrinkLiteDto>
)