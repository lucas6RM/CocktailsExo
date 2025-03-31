package com.mercierlucas.cocktailsexo.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.cocktailsexo.MyApp
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsRoom
import com.mercierlucas.cocktailsexo.data.local.model.DrinkLiteEntity
import com.mercierlucas.cocktailsexo.data.network.api.ApiService
import com.mercierlucas.cocktailsexo.data.network.dtos.DrinkLiteDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {

    private val _drinkDetailsRoomListLiveData = MutableLiveData<List<DrinkDetailsRoom>>(emptyList())
    val drinkDetailsRoomListLiveData : MutableLiveData<List<DrinkDetailsRoom>> = _drinkDetailsRoomListLiveData

    private val _drinkLiteEntityListLiveData = MutableLiveData<List<DrinkLiteEntity>>(emptyList())
    val drinkLiteEntityListLiveData : MutableLiveData<List<DrinkLiteEntity>> = _drinkLiteEntityListLiveData


    private val _remoteDrinksListLiveData = MutableLiveData<List<DrinkLiteDto>?>(emptyList())
    val remoteDrinksListLiveData: MutableLiveData<List<DrinkLiteDto>?> = _remoteDrinksListLiveData

    init {

        getAllDrinksDetailedRoom()
        val listeRoom = drinkDetailsRoomListLiveData.value?.map { drinkDetailed ->
            DrinkLiteEntity(
                idDrink = drinkDetailed.idDrink.toString(),
                strDrink = drinkDetailed.strDrink,
                strDrinkThumb = drinkDetailed.strDrinkThumb,
                isMine = drinkDetailed.isMine)
        }


        Log.i("Lucas_entity", listeRoom.toString())






/*
        deletedByIdDrinkDetailedRoom(4)

        deletedByIdDrinkDetailedRoom(5)

        deletedByIdDrinkDetailedRoom(6)



*/
        /*

        insertDrinkDetailedRoom(
            DrinkDetailsRoom(
            0,
            "pastis",
            "urlimage1",
            "2cl de jaune + eau",
            "d'abord jaune"
            )
        )
        insertDrinkDetailedRoom(DrinkDetailsRoom(
            0,
            "get",
            "urlimage2",
            "2cl de get",
            "ajouter glacon"
        ))
        insertDrinkDetailedRoom(DrinkDetailsRoom(
            0,
            "vodka redbull",
            "urlimage3",
            "2cl de vodka",
            "blabla"
        ))


        getAllDrinksDetailedRoom()*/





    }



    fun getRemoteDrinks() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                apiService.getAllCocktails()
            }
            response?.let {
                if (response.isSuccessful) {
                    val result = response.body()
                    _remoteDrinksListLiveData.value = result?.drinksLiteDto

                    Log.i("MAIN VM", "MAIN VM Liste Drinks Lite : " + result)
                } else when (response.code()) {
                    403 -> {
                        Log.i("MAIN VM", "Erreur 403")
                    }

                    404 -> {
                        Log.i("MAIN VM", "Erreur 404")
                    }

                    500 -> {
                        Log.i("MAIN VM", "Erreur 500")
                    }

                    else -> {
                        Log.i("MAIN VM", "Erreur inconnue")
                    }
                }
            }

        }
    }


    fun getAllDrinksDetailedRoom(){
        viewModelScope.launch {
            _drinkDetailsRoomListLiveData.value = emptyList<DrinkDetailsRoom>()

            val drinkList = withContext(Dispatchers.IO) {
                MyApp.db.drinkDetailsDao().getAll()
            }
            _drinkDetailsRoomListLiveData.value = drinkList.toList()

            Log.i("LUCAS", drinkList.toString())
        }
    }




    fun insertDrinkDetailedRoom(drinkDetailedRoom : DrinkDetailsRoom){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MyApp.db.drinkDetailsDao().insert(drinkDetailedRoom)
                Log.i("LUCAS", "insert details")
            }
        }
    }



    fun deletedByIdDrinkDetailedRoom(id: Long){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                MyApp.db.drinkDetailsDao().deleteById(idDrink = id)
                Log.i("LUCAS", "supprimé ${id}" )
            }
        }
    }

}