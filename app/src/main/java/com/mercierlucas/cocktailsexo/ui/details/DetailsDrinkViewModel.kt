package com.mercierlucas.cocktailsexo.ui.details

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.cocktailsexo.MyApp
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsDao
import com.mercierlucas.cocktailsexo.data.local.model.DrinkDetailsModel
import com.mercierlucas.cocktailsexo.data.local.model.DrinkLiteModel
import com.mercierlucas.cocktailsexo.data.network.api.ApiService
import com.mercierlucas.cocktailsexo.data.network.dtos.DrinkDetailsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class DetailsDrinkViewModel @Inject constructor(
    private val apiService: ApiService,
    private val drinkDetailsDao: DrinkDetailsDao
) : ViewModel() {

    private val _drinkDetailsLiveData = MutableLiveData<DrinkDetailsModel?>()
    val drinkDetailsLiveData : MutableLiveData<DrinkDetailsModel?> = _drinkDetailsLiveData

    private val _messageFromGetDrinkResponse = MutableLiveData<Int?>()
    val messageFromGetDrinkResponse : LiveData<Int?>
        get() = _messageFromGetDrinkResponse




    fun getDrinkRoomById(idDrink: Long){
        viewModelScope.launch {
            val drinkRoom = withContext(Dispatchers.IO) {
                drinkDetailsDao.findByIdDrink(idDrink)
            }
            _drinkDetailsLiveData.value = DrinkDetailsModel(
                strDrink = drinkRoom.strDrink,
                strDrinkThumb = drinkRoom.strDrinkThumb,
                strIngredients = drinkRoom.strIngredients,
                strInstructions = drinkRoom.strInstructions
            )
        }
    }

    fun getDrinkRemoteById(idDrink: Long){
        viewModelScope.launch {
            try {
                val responseGetCocktail = withContext(Dispatchers.IO) {
                    apiService.getCocktail(idDrink)
                }
                val body = responseGetCocktail?.body()
                when {
                    responseGetCocktail == null ->
                        Log.e(TAG, "Pas de reponse du serveur")

                    responseGetCocktail.isSuccessful && body != null -> {

                        body.drinksDetailsDto[0]?.let { drinkDetailsDto ->
                            _drinkDetailsLiveData.value = DrinkDetailsModel(
                                strDrink = drinkDetailsDto.strDrink ?: "",
                                strDrinkThumb = drinkDetailsDto.strDrinkThumb ?: "",
                                strIngredients = drinkDetailsDto.ingredients,
                                strInstructions = drinkDetailsDto.strInstructions ?: ""
                            )
                        }
                        _messageFromGetDrinkResponse.value = responseGetCocktail.code()

                    }

                    else -> responseGetCocktail.errorBody()?.let { Log.e(TAG, it.string()) }
                }
            }catch (error : ConnectException){
                _messageFromGetDrinkResponse.value = 505
            }
        }
    }



}