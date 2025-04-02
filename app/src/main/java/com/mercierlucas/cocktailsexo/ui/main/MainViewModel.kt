package com.mercierlucas.cocktailsexo.ui.main

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.cocktailsexo.MyApp
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsDao
import com.mercierlucas.cocktailsexo.data.local.daos.DrinkDetailsRoom
import com.mercierlucas.cocktailsexo.data.local.model.DrinkLiteModel
import com.mercierlucas.cocktailsexo.data.network.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService,
    private val drinkDetailsDao: DrinkDetailsDao
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
    }

    fun refreshFullDrinkList() {
        val listFull = drinkDetailsRoomListLiveData.value?.toMutableList()
        val listRemote = remoteDrinksListLiveData.value?.toList()

        if (listRemote != null) {
            listFull?.addAll(listRemote)
            listFull?.sortBy { it.strDrink }
            _allDrinksLiveData.value = listFull
        }
    }


    fun getRemoteDrinks() {
        viewModelScope.launch {
            try{
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
            } catch (error : ConnectException){
                _messageFromGetAllDrinksResponse.value = 505
            }
        }
    }


    fun getAllDrinksDetailedRoom(){
        viewModelScope.launch {

            val drinkList = withContext(Dispatchers.IO) {
                drinkDetailsDao.getAll()
            }
            _drinkDetailsRoomListLiveData.value = drinkList.map { drinkDetailed ->
                DrinkLiteModel(
                    idDrink = drinkDetailed.idDrink.toString(),
                    strDrink = drinkDetailed.strDrink,
                    strDrinkThumb = drinkDetailed.strDrinkThumb,
                    isMine = drinkDetailed.isMine)
            }
            refreshFullDrinkList()
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