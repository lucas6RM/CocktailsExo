package com.mercierlucas.cocktailsexo.ui.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.cocktailsexo.MyApp
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsDao
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateDrinkViewModel @Inject constructor(
    private val drinkDetailsDao: DrinkDetailsDao
) : ViewModel() {

    private val _isNewDrinkCreated = MutableLiveData(false)
    val isNewDrinkCreated: LiveData<Boolean>
        get() = _isNewDrinkCreated

    private val _messageFromRoom = MutableLiveData<String>()
    val messageFromRoom : LiveData<String>
        get() = _messageFromRoom

    init {
        _isNewDrinkCreated.value = false
    }

    fun insertDrinkDetailedRoom(drinkDetailedRoom : DrinkDetailsRoom){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                drinkDetailsDao.insert(drinkDetailedRoom)
            }
            _isNewDrinkCreated.value = true
            _messageFromRoom.value = "Personal cocktail created"
        }
    }

}