package com.mercierlucas.cocktailsexo.data.network.api

import com.mercierlucas.cocktailsexo.data.network.dtos.ResponseAllDrinksLite
import com.mercierlucas.cocktailsexo.data.network.dtos.ResponseDrinkDetailsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(ApiRoutes.ALL_COCKTAILS)
    suspend fun getAllCocktails(): Response<ResponseAllDrinksLite>?

    @GET(ApiRoutes.GET_COCKTAIL)
    suspend fun getCocktail(
        @Query("i") idDrink : Long,
    ): Response<ResponseDrinkDetailsDto>?


}