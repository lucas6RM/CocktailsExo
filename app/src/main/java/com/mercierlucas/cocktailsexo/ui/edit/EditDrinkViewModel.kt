package com.mercierlucas.cocktailsexo.ui.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.cocktailsexo.MyApp
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsDao
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsRoom
import com.mercierlucas.cocktailsexo.data.local.model.DrinkDetailsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditDrinkViewModel @Inject constructor(
    private val drinkDetailsDao: DrinkDetailsDao
) : ViewModel() {

    private val _drinkDetailsLiveData = MutableLiveData<DrinkDetailsModel?>()
    val drinkDetailsLiveData : MutableLiveData<DrinkDetailsModel?> = _drinkDetailsLiveData

    private val _isDrinkUpdated = MutableLiveData(false)
    val isDrinkUpdated: LiveData<Boolean>
        get() = _isDrinkUpdated

    private val _isDrinkDeleted = MutableLiveData(false)
    val isDrinkDeleted: LiveData<Boolean>
        get() = _isDrinkDeleted

    private val _messageFromRoom = MutableLiveData<String>()
    val messageFromRoom : LiveData<String>
        get() = _messageFromRoom

    init {
        _isDrinkUpdated.value = false
        _isDrinkDeleted.value = false
    }


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

    fun updateDrinkRoom(drinkDetailsRoom: DrinkDetailsRoom){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                drinkDetailsDao.update(drinkDetailsRoom)
            }
            _isDrinkUpdated.value = true
            _messageFromRoom.value = "Personal cocktail updated"
        }
    }

    fun deletedByIdDrinkDetailedRoom(id: Long){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                drinkDetailsDao.deleteById(idDrink = id)
            }
            _isDrinkDeleted.value = true
            _messageFromRoom.value = "Personal cocktail deleted"
        }
    }
}