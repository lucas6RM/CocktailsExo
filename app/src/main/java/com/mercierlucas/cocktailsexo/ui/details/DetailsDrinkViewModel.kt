package com.mercierlucas.cocktailsexo.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.cocktailsexo.MyApp
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsRoom
import com.mercierlucas.cocktailsexo.data.local.model.DrinkDetailsModel
import com.mercierlucas.cocktailsexo.data.local.model.DrinkLiteModel
import com.mercierlucas.cocktailsexo.data.network.dtos.DrinkDetailsDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsDrinkViewModel : ViewModel() {

    private val _drinkDetailsLiveData = MutableLiveData<DrinkDetailsModel?>()
    val drinkDetailsLiveData : MutableLiveData<DrinkDetailsModel?> = _drinkDetailsLiveData


    fun getDrinkRemoteById(idDrink: String){

    }

    fun getDrinkRoomById(idDrink: Long){
        viewModelScope.launch {

            val drinkRoom = withContext(Dispatchers.IO) {
                MyApp.db.drinkDetailsDao().findByIdDrink(idDrink)
            }
            _drinkDetailsLiveData.value = DrinkDetailsModel(
                strDrink = drinkRoom.strDrink,
                strDrinkThumb = drinkRoom.strDrinkThumb,
                strIngredients = drinkRoom.strIngredients,
                strInstructions = drinkRoom.strInstructions
            )
        }
    }


}