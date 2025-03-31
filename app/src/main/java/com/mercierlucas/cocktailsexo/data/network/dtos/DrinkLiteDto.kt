package com.mercierlucas.cocktailsexo.data.network.dtos

data class DrinkLiteDto(

//    @Json(name = "idDrink")
    val idDrink: String,
//    @Json(name = "strDrink")
    val strDrink: String,
//    @Json(name = "strDrinkThumb")
    val strDrinkThumb: String,

    val isMine: Boolean = false
)