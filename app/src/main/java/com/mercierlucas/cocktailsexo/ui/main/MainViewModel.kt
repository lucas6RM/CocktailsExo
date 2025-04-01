package com.mercierlucas.cocktailsexo.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.cocktailsexo.MyApp
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsRoom
import com.mercierlucas.cocktailsexo.data.local.model.DrinkLiteModel
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

    private val _drinkDetailsRoomListLiveData = MutableLiveData<List<DrinkLiteModel>>(emptyList())
    val drinkDetailsRoomListLiveData : MutableLiveData<List<DrinkLiteModel>> = _drinkDetailsRoomListLiveData

    private val _drinkLiteModelListLiveData = MutableLiveData<List<DrinkLiteModel>>(emptyList())
    val drinkLiteModelListLiveData : MutableLiveData<List<DrinkLiteModel>> = _drinkLiteModelListLiveData


    private val _remoteDrinksListLiveData = MutableLiveData<List<DrinkLiteModel>?>(emptyList())
    val remoteDrinksListLiveData: MutableLiveData<List<DrinkLiteModel>?> = _remoteDrinksListLiveData

    private val _messageFromGetAllDrinksResponse = MutableLiveData<String?>()
    val messageFromGetAllDrinksResponse : LiveData<String?>
        get() = _messageFromGetAllDrinksResponse


    init {

        getAllDrinksDetailedRoom()

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
                    _remoteDrinksListLiveData.value = result?.drinksLiteDto?.map { drinkRemote ->
                        DrinkLiteModel(
                            idDrink = drinkRemote.idDrink,
                            strDrink = drinkRemote.strDrink,
                            strDrinkThumb = drinkRemote.strDrinkThumb,
                            isMine = drinkRemote.isMine)
                    }

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

            val drinkList = withContext(Dispatchers.IO) {
                MyApp.db.drinkDetailsDao().getAll()
            }
            _drinkDetailsRoomListLiveData.value = drinkList.map { drinkDetailed ->
                DrinkLiteModel(
                    idDrink = drinkDetailed.idDrink.toString(),
                    strDrink = drinkDetailed.strDrink,
                    strDrinkThumb = drinkDetailed.strDrinkThumb,
                    isMine = drinkDetailed.isMine)
            }

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

    fun clickOnDrinkIsDone(idDrink: Long, isMine: Boolean) {
        if (isMine)
            goToDetailsRoom(idDrink)
        else
            goToDetailsRemote(idDrink)
    }

    private fun goToDetailsRemote(idDrink: Long) {

    }

    private fun goToDetailsRoom(idDrink: Long) {

    }


}