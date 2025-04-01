package com.mercierlucas.cocktailsexo.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class DrinkDetailsModel(

    val strDrink: String,

    val strDrinkThumb: String,

    val strIngredients: String,

    val strInstructions: String,

)
