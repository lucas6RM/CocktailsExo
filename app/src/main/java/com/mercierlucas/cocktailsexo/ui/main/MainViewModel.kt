package com.mercierlucas.cocktailsexo.ui.main

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.cocktailsexo.MyApp
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsRoom
import com.mercierlucas.cocktailsexo.data.local.model.DrinkLiteModel
import com.mercierlucas.cocktailsexo.data.network.api.ApiService
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

    private val _allDrinksLiveData = MutableLiveData<List<DrinkLiteModel>?>(emptyList())
    val allDrinksLiveData : MutableLiveData<List<DrinkLiteModel>?> = _allDrinksLiveData

    private val _remoteDrinksListLiveData = MutableLiveData<List<DrinkLiteModel>?>(emptyList())
    val remoteDrinksListLiveData: MutableLiveData<List<DrinkLiteModel>?> = _remoteDrinksListLiveData

    private val _messageFromGetAllDrinksResponse = MutableLiveData<Int?>()
    val messageFromGetAllDrinksResponse : LiveData<Int?>
        get() = _messageFromGetAllDrinksResponse


    init {

        getAllDrinksDetailedRoom()
        getRemoteDrinks()

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

    fun refreshFullDrinkList() {
        val listeRoom = drinkDetailsRoomListLiveData.value?.toMutableList()
        val listeRemote = remoteDrinksListLiveData.value?.toList()

        if (listeRemote != null) {
            listeRoom?.addAll(listeRemote)
            _allDrinksLiveData.value = listeRoom
        }
    }


    fun getRemoteDrinks() {
        viewModelScope.launch {
            val responseGetAllCocktails = withContext(Dispatchers.IO) {
                apiService.getAllCocktails()
            }
            val body = responseGetAllCocktails?.body()
            when{
                responseGetAllCocktails == null ->
                    Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                responseGetAllCocktails.isSuccessful && body != null -> {
                    _remoteDrinksListLiveData.value = body.drinksLiteDto.map { drinkRemote ->
                        DrinkLiteModel(
                            idDrink = drinkRemote.idDrink,
                            strDrink = drinkRemote.strDrink,
                            strDrinkThumb = drinkRemote.strDrinkThumb,
                            isMine = drinkRemote.isMine)
                    }
                    _messageFromGetAllDrinksResponse.value = responseGetAllCocktails.code()
                    refreshFullDrinkList()
                }
                else -> responseGetAllCocktails.errorBody()?.let { Log.e(ContentValues.TAG, it.string()) }
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
        }
    }



    fun clickOnDrinkIsDone(idDrink: String, isMine: Boolean) {
        if (isMine)
            goToDetailsRoom(idDrink)
        else
            goToDetailsRemote(idDrink)
    }

    private fun goToDetailsRemote(idDrink: String) {

    }

    private fun goToDetailsRoom(idDrink: String) {

    }


}