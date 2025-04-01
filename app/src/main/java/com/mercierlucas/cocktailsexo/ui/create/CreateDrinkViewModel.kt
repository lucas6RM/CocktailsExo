package com.mercierlucas.cocktailsexo.ui.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.cocktailsexo.MyApp
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateDrinkViewModel : ViewModel() {

    private val _isNewDrinkCreated = MutableLiveData(false)
    val isNewDrinkCreated: LiveData<Boolean>
        get() = _isNewDrinkCreated

    init {
        _isNewDrinkCreated.value = false
    }

    fun insertDrinkDetailedRoom(drinkDetailedRoom : DrinkDetailsRoom){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MyApp.db.drinkDetailsDao().insert(drinkDetailedRoom)
            }
            _isNewDrinkCreated.value = true
        }
    }

}